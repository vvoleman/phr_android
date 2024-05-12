package cz.vvoleman.phr.featureMedicine.presentation.provider

import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicineSchedulePresentationModel

interface ProblemCategoryDetailProvider {

    suspend fun getBindingItems(
        items: List<MedicineSchedulePresentationModel>,
        onClick: (String) -> Unit
    ): SectionContainer
}
