package cz.vvoleman.phr.featureMedicine.ui.export.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.DocumentPageMedicineBinding
import cz.vvoleman.phr.featureMedicine.ui.export.adapter.MedicinePageAdapter
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.MedicineScheduleUiModel
import java.time.LocalDateTime

class MedicineExportPage(
    private val context: Context,
    private val schedules: List<MedicineScheduleUiModel>
) : DocumentPage() {

    override fun bind(binding: ViewBinding) {
        val viewBinding = binding as DocumentPageMedicineBinding

        val items = schedules.sortedBy { it.createdAt }
        val startedAt = items.first().createdAt.toLocalDate().toLocalString()
        val endedAt = items.last().createdAt.toLocalDate().toLocalString()

        val details = getDetails()!!

        viewBinding.textViewTimeRange.text =
            context.resources.getString(R.string.document_medicine_time_range, startedAt, endedAt)

        viewBinding.apply {
            textViewGeneratedAt.text = context.resources.getString(
                R.string.document_medicine_generated_at,
                LocalDateTime.now().toLocalString()
            )
            textViewPatientName.text = items.first().patient.name
            textViewPage.text = "${details.currentPage}/${details.totalPages}"
        }

        Log.d("MedicineExportPage", "bind: $items")
        val medicineAdapter = MedicinePageAdapter()
        viewBinding.recyclerViewMedicines.apply {
            adapter = medicineAdapter
            this.addItemDecoration(MarginItemDecoration(8))
            setHasFixedSize(false)
        }

        medicineAdapter.submitList(items)

    }

    override fun getViewBinding(inflater: LayoutInflater): DocumentPageMedicineBinding {
        return DocumentPageMedicineBinding.inflate(inflater)
    }
}
