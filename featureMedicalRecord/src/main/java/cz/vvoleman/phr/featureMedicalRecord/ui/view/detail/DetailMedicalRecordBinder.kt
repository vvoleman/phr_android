package cz.vvoleman.phr.featureMedicalRecord.ui.view.detail

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentDetailMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AssetPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.model.ImageItemUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter.ImageAdapter

class DetailMedicalRecordBinder :
    BaseViewStateBinder<DetailMedicalRecordViewState, FragmentDetailMedicalRecordBinding, DetailMedicalRecordBinder.Notification>(),
    ImageAdapter.FileAdapterListener {


    override fun firstBind(viewBinding: FragmentDetailMedicalRecordBinding, viewState: DetailMedicalRecordViewState) {
        super.firstBind(viewBinding, viewState)

        val resources = viewBinding.root.resources
        val record = viewState.medicalRecord

        viewBinding.textViewProblemCategory.apply {
            text = record.problemCategory?.name ?: resources.getString(R.string.no_problem_category)
            val color = record.problemCategory?.let { Color.parseColor(it.color) } ?: resources.getColor(
                R.color.no_category,
                null
            )
            setBackgroundColor(color)
        }

        viewBinding.textViewMedicalWorker.text =
            record.specificMedicalWorker?.toString() ?: resources.getString(R.string.no_medical_worker)
        viewBinding.textViewDiagnose.text = record.diagnose?.name ?: resources.getString(R.string.no_diagnose)

        val fileAdapter = ImageAdapter(this, false)
        val assets = record.assets.map { ImageItemUiModel(AssetPresentationModel(it.id, it.createdAt, it.url)) }

        viewBinding.recyclerViewFiles.apply {
            adapter = fileAdapter
            layoutManager = LinearLayoutManager(viewBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(false)
        }

        fileAdapter.submitList(assets)
    }

    sealed class Notification {
        data class FileClicked(val item: ImageItemUiModel) : Notification()
    }

    override fun onFileClicked(item: ImageItemUiModel) {
        notify(Notification.FileClicked(item))
    }

    override fun onFileDeleted(item: ImageItemUiModel) {}
}
