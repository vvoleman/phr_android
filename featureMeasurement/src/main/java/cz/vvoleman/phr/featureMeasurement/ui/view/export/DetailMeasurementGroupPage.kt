package cz.vvoleman.phr.featureMeasurement.ui.view.export

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.xwray.groupie.GroupieAdapter
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMeasurement.databinding.DocumentPageDetailBinding
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.export.ExportDetailParamsPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.factory.DocumentTableFactory

class DetailMeasurementGroupPage(
    private val params: ExportDetailParamsPresentationModel
) : DocumentPage() {

    override fun bind(binding: ViewBinding) {
        val viewBinding = binding as DocumentPageDetailBinding

        val pageDetails = getDetails()!!

        val entries = params.entries.map { it.entry.createdAt }
        viewBinding.textViewGroupName.text = params.measurementGroup.name
        viewBinding.textViewTimeRange.text =
            "${entries.first().toLocalDate().toLocalString()} - ${entries.last().toLocalDate().toLocalString()}"

        viewBinding.textViewPatientName.text = params.measurementGroup.patient.name
        viewBinding.textViewPage.text = "${pageDetails.currentPage}/${pageDetails.totalPages}"
        viewBinding.textViewGeneratedAt.text = pageDetails.generatedAt.toLocalString()

        val containers = DocumentTableFactory().create(params.entries)

        val tableAdapter = GroupieAdapter().apply { add(containers) }
        viewBinding.recyclerViewEntries.apply {
            adapter = tableAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): DocumentPageDetailBinding {
        return DocumentPageDetailBinding.inflate(inflater)
    }
}
