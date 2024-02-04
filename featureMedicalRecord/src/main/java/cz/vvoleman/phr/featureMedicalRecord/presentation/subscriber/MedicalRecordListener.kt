package cz.vvoleman.phr.featureMedicalRecord.presentation.subscriber

import android.content.Context
import android.net.Uri
import android.util.Log
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryInfoDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.request.DataDeleteType
import cz.vvoleman.phr.common.presentation.event.GetMedicalFacilitiesAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.DeleteProblemCategoryEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoriesAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoryDetailSectionEvent
import cz.vvoleman.phr.common.presentation.eventBus.CommonEventBus
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.common.utils.localizedDiff
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByFacilityRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByMedicalWorkerRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByProblemCategoryRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.UpdateMedicalRecordProblemCategoryRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.DeleteMedicalRecordUseCase
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.core.MedicalRecordPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.provider.ProblemCategoryDetailProvider
import java.time.LocalDate

class MedicalRecordListener(
    private val commonEventBus: CommonEventBus,
    private val problemCategoryDetailProvider: ProblemCategoryDetailProvider,
    private val getMedicalRecordByMedicalWorkerRepository: GetMedicalRecordByMedicalWorkerRepository,
    private val getMedicalRecordByFacilityRepository: GetMedicalRecordByFacilityRepository,
    private val getMedicalRecordByCategoryRepository: GetMedicalRecordByProblemCategoryRepository,
    private val updateMedicalRecordProblemCategoryRepository: UpdateMedicalRecordProblemCategoryRepository,
    private val deleteMedicalRecordUseCase: DeleteMedicalRecordUseCase,
    private val medicalRecordMapper: MedicalRecordPresentationModelToDomainMapper,
    private val context: Context
) : ModuleListener() {
    override val TAG: String = "MedicalRecordListener"

    override suspend fun onInit() {
        super.onInit()
        Log.d(TAG, "onInit")
        commonEventBus.getWorkerAdditionalInfoBus.addListener(TAG) {
            return@addListener onGetMedicalWorkersAdditionalInfoEvent(it)
        }
        commonEventBus.getFacilityAdditionalInfoBus.addListener(TAG) {
            return@addListener onGetMedicalFacilityAdditionalInfoEvent(it)
        }
        commonEventBus.getCategoryAdditionalInfoBus.addListener(TAG) {
            return@addListener onGetProblemCategoriesAdditionalInfoEvent(it)
        }
        commonEventBus.deleteProblemCategoryBus.addListener(TAG) {
            return@addListener onDeleteProblemCategoryEvent(it)
        }
        commonEventBus.getCategoryDetailSection.addListener(TAG) {
            return@addListener onGetProblemCategoryDetailSectionEvent(it)
        }
    }

    private suspend fun onGetMedicalWorkersAdditionalInfoEvent(
        event: GetMedicalWorkersAdditionalInfoEvent
    ): Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>> {
        val map = mutableMapOf<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>()

        event.medicalWorker.forEach { worker ->
            val records = getMedicalRecordByMedicalWorkerRepository.getMedicalRecordsByMedicalWorker(worker)

            val documentPlural =
                context.resources.getQuantityString(cz.vvoleman.phr.common_datasource.R.plurals.document, records.size)

            map[worker] = listOf(
                AdditionalInfoDomainModel(
                    icon = R.drawable.ic_document,
                    text = "${records.size} $documentPlural",
                    onClick = {
                        val uri = Uri.parse("phr://listMedicalRecord/?medicalWorkerId=${worker.id}")
                        navController?.navigate(uri)
                    }
                )
            )
        }

        return map.toMap()
    }

    private suspend fun onGetMedicalFacilityAdditionalInfoEvent(
        event: GetMedicalFacilitiesAdditionalInfoEvent
    ): Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>> {
        val map =
            mutableMapOf<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>>()

        val allRecords = getMedicalRecordByFacilityRepository.getMedicalRecordsByFacility(
            event.medicalFacilities.map { it.id }.distinct(),
            event.patientId
        )

        event.medicalFacilities.forEach { facility ->
            val records = allRecords[facility.id] ?: listOf()

            val documentPlural =
                context.resources.getQuantityString(cz.vvoleman.phr.common_datasource.R.plurals.document, records.size)

            map[facility] = listOf(
                AdditionalInfoDomainModel(
                    icon = R.drawable.ic_document,
                    text = "${records.size} $documentPlural",
                    onClick = if (records.isNotEmpty()) {
                        {
                            val uri = Uri.parse("phr://listMedicalRecord/?medicalFacilityId=${facility.id}")
                            navController?.navigate(uri)
                        }
                    } else {
                        null
                    }
                )
            )
        }

        return map.toMap()
    }

    private suspend fun onGetProblemCategoriesAdditionalInfoEvent(
        event: GetProblemCategoriesAdditionalInfoEvent
    ): Map<ProblemCategoryDomainModel, ProblemCategoryInfoDomainModel> {
        val map = mutableMapOf<ProblemCategoryDomainModel, ProblemCategoryInfoDomainModel>()

        val ids = event.problemCategories.map { it.id }.distinct()
        val allRecords = getMedicalRecordByCategoryRepository.getMedicalRecordByProblemCategory(
            event.problemCategories.map { it.id }.distinct(),
        )

        event.problemCategories.forEach { category ->
            val records = allRecords[category.id] ?: listOf()

            val documentPlural =
                context.resources.getQuantityString(cz.vvoleman.phr.common_datasource.R.plurals.document, records.size)

            val lastEditedAt = records.maxByOrNull { it.createdAt }?.createdAt
            val text = lastEditedAt?.let {
                context.resources.getString(R.string.label_last_edited, LocalDate.now().localizedDiff(it))
            } ?: context.resources.getString(R.string.label_last_edited_no_data)

            val secondaries: List<AdditionalInfoDomainModel<ProblemCategoryDomainModel>> = if (lastEditedAt != null) {
                listOf(
                    AdditionalInfoDomainModel(
                        icon = cz.vvoleman.phr.common_datasource.R.drawable.ic_time,
                        text = text,
                        onClick = null
                    )
                )
            } else emptyList()

            map[category] =
                ProblemCategoryInfoDomainModel(
                    mainSlot = Pair(records.size, documentPlural),
                    secondarySlots = secondaries
                )
        }

        return map.toMap()
    }

    private suspend fun onDeleteProblemCategoryEvent(event: DeleteProblemCategoryEvent) {
        Log.d(TAG, "onDeleteProblemCategoryEvent")
        val records =
            getMedicalRecordByCategoryRepository.getMedicalRecordByProblemCategory(event.problemCategory.id)
        when (event.deleteType) {
            is DataDeleteType.DeleteData -> {
                records.forEach {
                    deleteMedicalRecordUseCase.executeInBackground(it.id)
                }
            }
            is DataDeleteType.MoveToAnother -> {
                val anotherCategory = (event.deleteType as DataDeleteType.MoveToAnother)
                records.forEach {
                    updateMedicalRecordProblemCategoryRepository.updateMedicalRecordProblemCategory(
                        medicalRecord = it,
                        problemCategory = anotherCategory.backupProblemCategory!!
                    )
                }
            }
        }
    }

    private suspend fun onGetProblemCategoryDetailSectionEvent(
        event: GetProblemCategoryDetailSectionEvent
    ): List<SectionContainer> {
        // Get all records for category
        val records = getMedicalRecordByCategoryRepository.getMedicalRecordByProblemCategory(event.problemCategory.id)
            .map { medicalRecordMapper.toPresentation(it) }

        val section = problemCategoryDetailProvider.getBindingItems(records) { id ->
            val uri = Uri.parse("phr://medicalRecordDetail/?medicalRecordId=$id")
//            navController?.navigate(uri)
        }

        return listOf(section)
    }
}
