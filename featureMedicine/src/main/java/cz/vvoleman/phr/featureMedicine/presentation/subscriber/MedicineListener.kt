package cz.vvoleman.phr.featureMedicine.presentation.subscriber

import android.content.Context
import android.net.Uri
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.common.domain.model.problemCategory.request.DataDeleteType
import cz.vvoleman.phr.common.presentation.event.problemCategory.DeleteProblemCategoryEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.ExportProblemCategoryEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoryDetailSectionEvent
import cz.vvoleman.phr.common.presentation.eventBus.CommonEventBus
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMedicine.domain.repository.GetSchedulesByProblemCategoryRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.UpdateMedicineScheduleProblemCategoryRepository
import cz.vvoleman.phr.featureMedicine.domain.usecase.DeleteMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.provider.ProblemCategoryDetailProvider
import cz.vvoleman.phr.featureMedicine.ui.export.view.MedicineExportPage
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineScheduleUiModelToPresentationMapper

class MedicineListener(
    private val commonEventBus: CommonEventBus,
    private val deleteMedicineScheduleUseCase: DeleteMedicineScheduleUseCase,
    private val getSchedulesByProblemCategoryRepository: GetSchedulesByProblemCategoryRepository,
    private val updateMedicineScheduleProblemCategoryRepository: UpdateMedicineScheduleProblemCategoryRepository,
    private val scheduleMapper: MedicineSchedulePresentationModelToDomainMapper,
    private val scheduleUiMapper: MedicineScheduleUiModelToPresentationMapper,
    private val problemCategoryDetailProvider: ProblemCategoryDetailProvider,
    private val context: Context,
) : ModuleListener() {

    override val TAG = "MedicineListener"

    override suspend fun onInit() {
        super.onInit()

        commonEventBus.getCategoryDetailSection.addListener(TAG) {
            return@addListener onGetCategoryDetailSection(it)
        }

        commonEventBus.exportProblemCategoryBus.addListener(TAG) {
            return@addListener onExportProblemCategory(it)
        }

        commonEventBus.deleteProblemCategoryBus.addListener(TAG) {
            return@addListener onDeleteProblemCategory(it)
        }
    }

    private suspend fun onDeleteProblemCategory(event: DeleteProblemCategoryEvent) {
        val schedules = getSchedulesByProblemCategoryRepository
            .getSchedulesByProblemCategory(event.problemCategory.id)

        when (event.deleteType) {
            is DataDeleteType.DeleteData -> {
                schedules.forEach {
                    deleteMedicineScheduleUseCase.executeInBackground(it.id)
                }
            }
            is DataDeleteType.MoveToAnother -> {
                val anotherCategory = (event.deleteType as DataDeleteType.MoveToAnother).backupProblemCategory
                schedules.forEach {
                    updateMedicineScheduleProblemCategoryRepository.updateMedicineScheduleProblemCategory(
                        medicineSchedule = it,
                        problemCategory = anotherCategory
                    )
                }
            }
        }

    }

    private suspend fun onGetCategoryDetailSection(
        event: GetProblemCategoryDetailSectionEvent
    ): List<SectionContainer> {
        // Get all records for category
        val schedules = getSchedulesByProblemCategoryRepository
            .getSchedulesByProblemCategory(event.problemCategory.id)
            .map { scheduleMapper.toPresentation(it) }

        val section = problemCategoryDetailProvider.getBindingItems(schedules) { id ->
            val uri = Uri.parse("phr://list/?medicalRecordId=$id")
//            navController?.navigate(uri)
        }

        return listOf(section)
    }

    private suspend fun onExportProblemCategory(
        event: ExportProblemCategoryEvent
    ): List<DocumentPage> {
        val schedules = getSchedulesByProblemCategoryRepository
            .getSchedulesByProblemCategory(event.problemCategory.id)
            .map { scheduleMapper.toPresentation(it) }

        val chunks = schedules.chunked(EXPORT_LIMIT_PER_PAGE)

        val pages = mutableListOf<DocumentPage>()
        for (chunk in chunks) {
            val page = MedicineExportPage(context, chunk.map { scheduleUiMapper.toUi(it) })
            pages.add(page)
        }

        return pages
    }

    companion object {
        const val EXPORT_LIMIT_PER_PAGE = 4
    }

}
