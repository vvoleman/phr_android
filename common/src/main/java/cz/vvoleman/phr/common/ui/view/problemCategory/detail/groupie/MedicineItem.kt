package cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.ItemDetailProblemCategoryMedicineBinding
import java.time.LocalDate

@Suppress("UnusedPrivateProperty")
class MedicineItem(
    private val title: String,
    private val id: String,
    private val dateFrom: LocalDate,
    private val dateTo: LocalDate,
) : BindableItem<ItemDetailProblemCategoryMedicineBinding>() {

    override fun bind(viewBinding: ItemDetailProblemCategoryMedicineBinding, position: Int) {
        viewBinding.apply {
            textViewName.text = title
            textViewDateFrom.text = dateFrom.toLocalString()
            textViewDateTo.text = dateTo.toLocalString()
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_detail_problem_category_medicine
    }

    override fun initializeViewBinding(view: View): ItemDetailProblemCategoryMedicineBinding {
        return ItemDetailProblemCategoryMedicineBinding.bind(view)
    }
}
