package cz.vvoleman.phr.common.domain.eventBus

import android.content.Context
import android.util.Log
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.common.domain.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilityByIdRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common_datasource.R

class CommonListener(
    private val commonEventBus: CommonEventBus,
    private val context: Context,
    private val getSpecificMedicalWorkersRepository: GetSpecificMedicalWorkersRepository,
    private val getFacilityByIdRepository: GetFacilityByIdRepository,
) : ModuleListener() {

    override val TAG: String = "CommonListener"

    override suspend fun onInit() {
        super.onInit()

        commonEventBus.getWorkerAdditionalInfoBus.addListener(TAG) {
            Log.d(TAG, "onGetMedicalWorkersAdditionalInfoEvent")
            return@addListener onGetMedicalWorkersAdditionalInfoEvent(it)
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

    override suspend fun onDestroy() {
        super.onDestroy()
    }
}
