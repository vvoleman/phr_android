package cz.vvoleman.phr.featureMedicalRecord.ui.provider.problemCategoryDetail

import android.content.Context
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.provider.ProblemCategoryDetailProvider
import dagger.hilt.android.qualifiers.ApplicationContext

class MedicalRecordProblemCategoryDetailProvider(
    @ApplicationContext private val context: Context,
) : ProblemCategoryDetailProvider {

    override suspend fun getBindingItems(
        items: List<MedicalRecordPresentationModel>,
        onClick: (String) -> Unit
    ): SectionContainer {
        val bindItems = items.map {
            MedicalRecordItem(
                medicalRecord = it,
                onClick = onClick
            )
        }

        val updatedAt = items.maxOfOrNull { it.createdAt }

        return SectionContainer(
            updatedAt = updatedAt,
            title = context.resources.getString(R.string.medical_record_problem_category_detail_title),
            icon = R.drawable.ic_document,
            description = context.resources.getString(R.string.medical_record_problem_category_detail_description),
            items = bindItems
        )
    }
}
