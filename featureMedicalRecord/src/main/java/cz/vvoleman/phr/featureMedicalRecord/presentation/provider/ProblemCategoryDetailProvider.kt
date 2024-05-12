package cz.vvoleman.phr.featureMedicalRecord.presentation.provider

import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordPresentationModel

interface ProblemCategoryDetailProvider {

    suspend fun getBindingItems(
        items: List<MedicalRecordPresentationModel>,
        onClick: (String) -> Unit
    ): SectionContainer
}
