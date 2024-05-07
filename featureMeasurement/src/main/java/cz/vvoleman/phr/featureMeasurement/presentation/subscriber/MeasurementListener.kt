package cz.vvoleman.phr.featureMeasurement.presentation.subscriber

import android.net.Uri
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoryDetailSectionEvent
import cz.vvoleman.phr.common.presentation.eventBus.CommonEventBus
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMeasurement.domain.model.detail.GetFieldStatsRequest
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupsByProblemCategoryRepository
import cz.vvoleman.phr.featureMeasurement.domain.usecase.detail.GetFieldStatsUseCase
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.detail.FieldStatsPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.MeasurementGroupWithStatsPresentationModel

class MeasurementListener(
    private val commonEventBus: CommonEventBus,
    private val fieldStatsUseCase: GetFieldStatsUseCase,
    private val getMeasurementsByProblemCategoryRepository: GetMeasurementGroupsByProblemCategoryRepository,
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
    private val fieldStatsMapper: FieldStatsPresentationModelToDomainMapper,
    private val problemCategoryDetailProvider: ProblemCategoryDetailProvider,
) : ModuleListener() {

    override val TAG = "MeasurementListener"

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
        val groups = getMeasurementsByProblemCategoryRepository
            .getMeasurementGroupsByProblemCategory(event.problemCategory.id)

        val stats = mutableListOf<MeasurementGroupWithStatsPresentationModel>()

        groups.forEach { group ->
            val request = GetFieldStatsRequest(
                measurementGroup = group
            )
            val result = fieldStatsUseCase.executeInBackground(request)
            stats.add(MeasurementGroupWithStatsPresentationModel(
                measurementGroup = measurementGroupMapper.toPresentation(group),
                fieldStats = fieldStatsMapper.toPresentation(result)
            ))
        }

        val section = problemCategoryDetailProvider.getBindingItems(stats) { id ->
            val uri = Uri.parse("phr://list/?medicalRecordId=$id")
//            navController?.navigate(uri)
        }

        return listOf(section)
    }

}
