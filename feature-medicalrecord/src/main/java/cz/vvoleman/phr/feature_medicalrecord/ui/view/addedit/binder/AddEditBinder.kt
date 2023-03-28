package cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit.binder

import android.content.Context
import android.util.Log
import android.view.View
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState
import cz.vvoleman.phr.feature_medicalrecord.ui.model.ImageItemUiModel
import cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit.ImageAdapter
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate


class AddEditBinder:
    BaseViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding, AddEditBinder.Notification>(),
    ImageAdapter.OnAdapterItemListener {

    private lateinit var adapter: ImageAdapter

    override fun bind(
        viewBinding: FragmentAddEditMedicalRecordBinding,
        viewState: AddEditViewState
    ) {
        viewBinding.datePicker.setDate(viewState.visitDate ?: LocalDate.now())
        viewBinding.textViewCurrentSizeFiles.text = viewState.files.size.toString()
        viewBinding.buttonAddFile.isEnabled = viewState.canAddMoreFiles()
        val items = viewState.files.map { ImageItemUiModel(it) }
        adapter.submitList(viewState.files.map { ImageItemUiModel(it) })
//        }
    }

    override fun init(viewBinding: FragmentAddEditMedicalRecordBinding, context: Context, lifecycleScope: CoroutineScope) {
        viewBinding.textViewTotalSizeFiles.text = AddEditViewState.MAX_FILES.toString()
        adapter = ImageAdapter(this)
        viewBinding.recyclerViewFiles.apply {
            adapter = adapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            setHasFixedSize(true)
        } //        viewBinding.buttonAddFile.setOnClickListener {
//            notify(Notification.AddFile)
//        }
    }

    sealed class Notification {
        object AddFile : Notification()
        data class FileClick(val item: ImageItemUiModel) : Notification()
        data class FileDelete(val item: ImageItemUiModel) : Notification()
    }

    override fun onItemClicked(item: ImageItemUiModel) {
        notify(Notification.FileClick(item))
    }

    override fun onItemDeleted(item: ImageItemUiModel) {
        notify(Notification.FileDelete(item))
    }
}