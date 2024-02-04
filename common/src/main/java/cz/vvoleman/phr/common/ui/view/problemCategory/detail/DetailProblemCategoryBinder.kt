package cz.vvoleman.phr.common.ui.view.problemCategory.detail

import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.problemCategory.detail.DetailProblemCategoryViewState
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.MedicineItem
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentDetailProblemCategoryBinding
import cz.vvoleman.phr.common_datasource.databinding.ItemDetailSectionBinding
import java.time.LocalDate

class DetailProblemCategoryBinder :
    BaseViewStateBinder<DetailProblemCategoryViewState, FragmentDetailProblemCategoryBinding, DetailProblemCategoryBinder.Notification>() {

    override fun firstBind(
        viewBinding: FragmentDetailProblemCategoryBinding,
        viewState: DetailProblemCategoryViewState
    ) {
        super.firstBind(viewBinding, viewState)

        val groupieAdapter = GroupieAdapter().apply {
            addAll(viewState.sections)
        }

        viewBinding.recyclerView.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(true)
        }
    }

    private fun getMedicineSection(): BindableItem<ItemDetailSectionBinding> {
        return SectionContainer(
            title = "Léky",
            icon = R.drawable.ic_health,
            description = "Medicine description",
            items = listOf(
                MedicineItem(
                    title = "Paralen",
                    id = "1",
                    dateFrom = LocalDate.now(),
                    dateTo = LocalDate.now().plusDays(5)
                ),
                MedicineItem(
                    title = "Ibuprofen",
                    id = "2",
                    dateFrom = LocalDate.now(),
                    dateTo = LocalDate.now().plusDays(5)
                ),
                MedicineItem(
                    title = "Aspirin strašně dlouhej název",
                    id = "3",
                    dateFrom = LocalDate.now(),
                    dateTo = LocalDate.now().plusDays(5)
                ),
                MedicineItem(
                    title = "Paralen",
                    id = "4",
                    dateFrom = LocalDate.now(),
                    dateTo = LocalDate.now().plusDays(5)
                ),
                MedicineItem(
                    title = "Ibuprofen",
                    id = "5",
                    dateFrom = LocalDate.now(),
                    dateTo = LocalDate.now().plusDays(5)
                ),
            )
        )
    }

    sealed class Notification {
    }

}
