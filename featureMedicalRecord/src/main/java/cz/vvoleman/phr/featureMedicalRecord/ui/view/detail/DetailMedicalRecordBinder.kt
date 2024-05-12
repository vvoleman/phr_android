package cz.vvoleman.phr.featureMedicalRecord.ui.view.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.factory.DetailMedicalWorkerContainerFactory
import cz.vvoleman.phr.common.ui.mapper.healthcare.MedicalFacilityUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common.utils.checkVisibility
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentDetailMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AssetPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.model.ImageItemUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter.ImageAdapter

class DetailMedicalRecordBinder(
    private val containerFactory: DetailMedicalWorkerContainerFactory,
    private val specificWorkerMapper: SpecificMedicalWorkerUiModelToPresentationMapper,
    private val facilityMapper: MedicalFacilityUiModelToPresentationMapper,
) :
    BaseViewStateBinder<DetailMedicalRecordViewState, FragmentDetailMedicalRecordBinding, DetailMedicalRecordBinder.Notification>(),
    ImageAdapter.FileAdapterListener {

    override fun firstBind(viewBinding: FragmentDetailMedicalRecordBinding, viewState: DetailMedicalRecordViewState) {
        super.firstBind(viewBinding, viewState)

        val resources = viewBinding.root.resources
        val record = viewState.medicalRecord

        viewBinding.chipProblemCategory.apply {
            text = record.problemCategory?.name ?: resources.getString(R.string.no_problem_category)
            val color = record.problemCategory?.let { Color.parseColor(it.color) } ?: resources.getColor(
                R.color.no_category,
                null
            )
            chipBackgroundColor = ColorStateList.valueOf(color)
        }

        viewBinding.chipCreatedAt.text = viewBinding.root.resources.getString(
            R.string.detail_medical_record_visited_at,
            record.createdAt.toLocalString()
        )

        if (record.specificMedicalWorker != null) {
            val containers = containerFactory.create(
                listOf(specificWorkerMapper.toUi(record.specificMedicalWorker)),
                listOf(facilityMapper.toUi(viewState.medicalFacility!!))
            )
            val groupieAdapter = GroupieAdapter().apply {
                addAll(containers)
            }
            viewBinding.recyclerViewMedicalWorkers.apply {
                adapter = groupieAdapter
                layoutManager = LinearLayoutManager(viewBinding.root.context)
                addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
                setHasFixedSize(true)
            }
        }

        viewBinding.textViewDiagnose.text = record.diagnose?.name ?: resources.getString(R.string.no_diagnose)

        val fileAdapter = ImageAdapter(this, false)
        val assets = record.assets.map { ImageItemUiModel(AssetPresentationModel(it.id, it.createdAt, it.url)) }

        viewBinding.recyclerViewFiles.apply {
            adapter = fileAdapter
            layoutManager = LinearLayoutManager(viewBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(
                MarginItemDecoration(
                    SizingConstants.MARGIN_SIZE,
                    orientation = LinearLayoutManager.HORIZONTAL
                )
            )
            setHasFixedSize(false)
        }

        fileAdapter.submitList(assets)

        viewBinding.layoutLoading.visibility = checkVisibility(false)
        viewBinding.layoutMain.visibility = checkVisibility(true)
    }

    sealed class Notification {
        data class FileClicked(val item: ImageItemUiModel) : Notification()
    }

    override fun onFileClicked(item: ImageItemUiModel) {
        notify(Notification.FileClicked(item))
        Log.d("DetailMedicalRecordBinder", "onFileClicked: ${item.asset.id}")
    }

    override fun onFileDeleted(item: ImageItemUiModel) {}
}
