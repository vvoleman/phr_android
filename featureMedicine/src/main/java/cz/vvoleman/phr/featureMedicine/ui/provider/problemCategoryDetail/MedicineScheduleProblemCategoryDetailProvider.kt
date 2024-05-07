package cz.vvoleman.phr.featureMedicine.ui.provider.problemCategoryDetail

import android.content.Context
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicineSchedulePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.provider.ProblemCategoryDetailProvider
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineScheduleUiModelToPresentationMapper
import dagger.hilt.android.qualifiers.ApplicationContext

class MedicineScheduleProblemCategoryDetailProvider(
    private val scheduleMapper: MedicineScheduleUiModelToPresentationMapper,
    @ApplicationContext private val context: Context,
) : ProblemCategoryDetailProvider {
    override suspend fun getBindingItems(
        items: List<MedicineSchedulePresentationModel>,
        onClick: (String) -> Unit
    ): SectionContainer {
        val bindItems = items.map {
            MedicineItem(
                medicineSchedule = scheduleMapper.toUi(it),
                onClick = onClick
            )
        }

        return SectionContainer(
            updatedAt = null,
            title = context.resources.getString(R.string.medicine_schedule_problem_category_detail_title),
            icon = cz.vvoleman.phr.common_datasource.R.drawable.ic_health,
            description = context.resources.getString(R.string.medicine_schedule_problem_category_detail_description),
            items = bindItems
        )
    }
}
