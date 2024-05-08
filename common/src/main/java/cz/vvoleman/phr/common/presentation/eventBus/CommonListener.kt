package cz.vvoleman.phr.common.presentation.eventBus

import android.content.Context
import android.util.Log
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryInfoDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.DeleteMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilityByIdRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.DeleteProblemCategoryRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoriesRepository
import cz.vvoleman.phr.common.presentation.event.DeletePatientEvent
import cz.vvoleman.phr.common.presentation.event.GetMedicalFacilitiesAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoriesAdditionalInfoEvent
import cz.vvoleman.phr.common_datasource.R

class CommonListener(
    private val commonEventBus: CommonEventBus,
    private val context: Context,
    private val getSpecificMedicalWorkersRepository: GetSpecificMedicalWorkersRepository,
    private val getFacilityByIdRepository: GetFacilityByIdRepository,
    private val getProblemCategoriesRepository: GetProblemCategoriesRepository,
    private val deleteMedicalWorkerRepository: DeleteMedicalWorkerRepository,
    private val deleteProblemCategoryRepository: DeleteProblemCategoryRepository
) : ModuleListener() {

    override val TAG: String = "CommonListener"

    override suspend fun onInit() {
        super.onInit()

        commonEventBus.getWorkerAdditionalInfoBus.addListener(TAG) {
            Log.d(TAG, "onGetMedicalWorkersAdditionalInfoEvent")
            return@addListener onGetMedicalWorkersAdditionalInfoEvent(it)
        }

        commonEventBus.getFacilityAdditionalInfoBus.addListener(TAG) {
            Log.d(TAG, "onGetMedicalFacilitiesAdditionalInfoEvent")
            return@addListener onGetMedicalFacilitiesAdditionalInfoEvent(it)
        }

        commonEventBus.getCategoryAdditionalInfoBus.addListener(TAG) {
            Log.d(TAG, "onGetProblemCategoriesAdditionalInfoEvent")
            return@addListener onGetProblemCategoriesAdditionalInfoEvent(it)
        }

        commonEventBus.deletePatientBus.addListener(TAG) {
            return@addListener onDeletePatientEvent(it)
        }
    }

    private suspend fun onDeletePatientEvent(event: DeletePatientEvent) {
        getSpecificMedicalWorkersRepository
            .getSpecificMedicalWorkersByPatient(event.patient.id)
            .forEach {
                deleteMedicalWorkerRepository.deleteMedicalWorker(it.medicalWorker)
            }

        getProblemCategoriesRepository
            .getProblemCategories(event.patient.id)
            .forEach {
                deleteProblemCategoryRepository.deleteProblemCategory(it.id)
            }
    }

    private suspend fun onGetMedicalWorkersAdditionalInfoEvent(
        event: GetMedicalWorkersAdditionalInfoEvent
    ): Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>> {
        val map = mutableMapOf<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>()

        event.medicalWorker.forEach { worker ->
            if (worker.id == null) return@forEach

            val workers = getSpecificMedicalWorkersRepository.getSpecificMedicalWorkers(worker.id)

            val text = if (workers.size == 1) {
                val facility = getFacilityByIdRepository
                    .getFacilityById(workers.first().medicalService.medicalFacilityId)
                facility?.fullName ?: return@forEach
            } else {
                context.getString(R.string.medical_facilities_count, workers.size)
            }

            map[worker] = workers.map {
                AdditionalInfoDomainModel(
                    icon = R.drawable.ic_hospital,
                    text = text
                )
            }
        }

        Log.d(TAG, "onGetMedicalWorkersAdditionalInfoEvent: $map")
        return map.toMap()
    }

    private suspend fun onGetMedicalFacilitiesAdditionalInfoEvent(
        event: GetMedicalFacilitiesAdditionalInfoEvent
    ): Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>> {
        val map =
            mutableMapOf<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>>()

        event.medicalFacilities.forEach { facility ->
            val workers = getSpecificMedicalWorkersRepository.getSpecificMedicalWorkersByFacility(facility.id)

            val text = if (workers.size == 1) {
                workers.first().medicalWorker.name
            } else {
                val countPlural =
                    context.resources.getQuantityString(R.plurals.medicalWorkers, workers.size)
                context.getString(R.string.medical_workers_count, workers.size, countPlural)
            }

            map[facility] = listOf(
                AdditionalInfoDomainModel(
                    icon = R.drawable.ic_person,
                    text = text
                )
            )
        }

        return map.toMap()
    }

    private fun onGetProblemCategoriesAdditionalInfoEvent(event: GetProblemCategoriesAdditionalInfoEvent): Map<ProblemCategoryDomainModel, ProblemCategoryInfoDomainModel> {
        return event.problemCategories.associateWith { category ->
            ProblemCategoryInfoDomainModel(
                mainSlot = Pair(15, "korun"),
                secondarySlots = emptyList(),
                priority = 0
            )
        }
    }

    override suspend fun onDestroy() {
        super.onDestroy()
    }
}
