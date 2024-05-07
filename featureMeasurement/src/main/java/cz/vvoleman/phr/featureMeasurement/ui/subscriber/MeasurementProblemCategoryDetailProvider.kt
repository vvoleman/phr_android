package cz.vvoleman.phr.featureMeasurement.ui.subscriber

import android.content.Context
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMeasurement.R
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.MeasurementGroupWithStatsPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.subscriber.ProblemCategoryDetailProvider
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.MeasurementGroupWithStatsUiModelToPresentationMapper
import dagger.hilt.android.qualifiers.ApplicationContext

class MeasurementProblemCategoryDetailProvider(
    private val measurementGroupMapper: MeasurementGroupWithStatsUiModelToPresentationMapper,
    @ApplicationContext private val context: Context,
) : ProblemCategoryDetailProvider {

    override suspend fun getBindingItems(
        items: List<MeasurementGroupWithStatsPresentationModel>,
        onClick: (String) -> Unit
    ): SectionContainer {
        val bindItems = items
            .map { measurementGroupMapper.toUi(it) }
            .map {
                MeasurementGroupItem(it, onClick)
            }

        return SectionContainer(
            updatedAt = null,
            title = context.resources.getString(R.string.measurement_problem_category_detail_title),
            icon = cz.vvoleman.phr.common_datasource.R.drawable.ic_measurement,
            description = context.resources.getString(R.string.measurement_problem_category_detail_description),
            items = bindItems
        )
    }
}
