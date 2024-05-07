package cz.vvoleman.phr.featureMedicine.ui.provider.problemCategoryDetail

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.utils.DateTimePattern
import cz.vvoleman.phr.common.utils.TimeConstants
import cz.vvoleman.phr.common.utils.toPattern
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ItemProblemCategoryDetailMedicineBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.MedicineScheduleUiModel
import java.time.format.TextStyle
import java.util.Locale

class MedicineItem(
    private val medicineSchedule: MedicineScheduleUiModel,
    private val onClick: (String) -> Unit
) : BindableItem<ItemProblemCategoryDetailMedicineBinding>() {

    override fun bind(viewBinding: ItemProblemCategoryDetailMedicineBinding, position: Int) {
        val times = medicineSchedule.getTimes().joinToString(SEPARATOR)
        val frequencies =
            medicineSchedule.getDays().map { it.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()) }
                .let {
                    if (it.size == TimeConstants.DAYS_IN_WEEK) {
                        viewBinding.root.context.getString(R.string.frequency_everyday)
                    } else {
                        it.joinToString(SEPARATOR)
                    }
                }

        viewBinding.apply {
            root.setOnClickListener {
                onClick(medicineSchedule.id)
            }
            textViewPackaging.text = medicineSchedule.medicine.packaging.form.name
            textViewName.text = medicineSchedule.medicine.name
            textViewTimes.text = times
            textViewFrequency.text = frequencies
            textViewExpired.text = viewBinding.root.context.getString(
                R.string.medicine_catalogue_item_expired,
                medicineSchedule.schedules.first().endingAt?.toPattern(DateTimePattern.DATE_TIME)
            )

            layoutExpired.visibility = if (medicineSchedule.isFinished) View.VISIBLE else View.GONE
            layoutInfo.visibility = if (!medicineSchedule.isFinished) View.VISIBLE else View.GONE
        }
    }

    override fun getLayout() = R.layout.item_problem_category_detail_medicine

    override fun initializeViewBinding(view: View): ItemProblemCategoryDetailMedicineBinding {
        return ItemProblemCategoryDetailMedicineBinding.bind(view)
    }

    companion object {
        const val SEPARATOR = ", "
    }
}
