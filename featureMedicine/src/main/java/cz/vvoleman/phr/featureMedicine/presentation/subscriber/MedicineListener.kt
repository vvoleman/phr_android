package cz.vvoleman.phr.featureMedicine.presentation.subscriber

import android.net.Uri
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoryDetailSectionEvent
import cz.vvoleman.phr.common.presentation.eventBus.CommonEventBus
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMedicine.domain.repository.GetSchedulesByProblemCategoryRepository
import cz.vvoleman.phr.featureMedicine.presentation.list.mapper.MedicineSchedulePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.presentation.provider.ProblemCategoryDetailProvider

class MedicineListener(
    private val commonEventBus: CommonEventBus,
    private val getSchedulesByProblemCategoryRepository: GetSchedulesByProblemCategoryRepository,
    private val scheduleMapper: MedicineSchedulePresentationModelToDomainMapper,
    private val problemCategoryDetailProvider: ProblemCategoryDetailProvider,
) : ModuleListener() {

    override val TAG = "MedicineListener"

    override suspend fun onInit() {
        super.onInit()

        commonEventBus.getCategoryDetailSection.addListener(TAG) {
            return@addListener onGetCategoryDetailSection(it)
        }
    }

    private suspend fun onGetCategoryDetailSection(
        event: GetProblemCategoryDetailSectionEvent
    ): List<SectionContainer> {
        // Get all records for category
        val records = getSchedulesByProblemCategoryRepository.getSchedulesByProblemCategory(event.problemCategory.id)
            .map { scheduleMapper.toPresentation(it) }

        val section = problemCategoryDetailProvider.getBindingItems(records) { id ->
            val uri = Uri.parse("phr://list/?medicalRecordId=$id")
//            navController?.navigate(uri)
        }

        return listOf(section)
    }


}
