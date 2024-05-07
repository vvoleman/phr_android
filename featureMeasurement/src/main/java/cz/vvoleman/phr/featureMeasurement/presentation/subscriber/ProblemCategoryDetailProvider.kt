package cz.vvoleman.phr.featureMeasurement.presentation.subscriber

import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.MeasurementGroupWithStatsPresentationModel

interface ProblemCategoryDetailProvider {

    suspend fun getBindingItems(
        items: List<MeasurementGroupWithStatsPresentationModel>,
        onClick: (String) -> Unit
    ): SectionContainer


}
