package cz.vvoleman.phr.featureMedicine.ui.export.view

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMedicine.databinding.DocumentPageMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportParamsPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.export.adapter.MedicinePageAdapter

class MedicineExportPage(
    private val params: ExportParamsPresentationModel
) : DocumentPage() {

    override fun bind(binding: ViewBinding) {
        val viewBinding = binding as DocumentPageMedicineBinding

        viewBinding.textViewTimeRange.text =
            "${params.startAt.toLocalDate().toLocalString()} - ${params.endAt.toLocalDate().toLocalString()}"

        val adapter = MedicinePageAdapter()

    }

    override fun getViewBinding(inflater: LayoutInflater): DocumentPageMedicineBinding {
        return DocumentPageMedicineBinding.inflate(inflater)
    }
}
