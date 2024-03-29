package cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.binder

import android.content.Context
import android.widget.ArrayAdapter
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.model.DiagnoseItemUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.model.ImageItemUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter.DiagnoseDialogSpinner
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter.ImageAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddEditBinder :
    BaseViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding, AddEditBinder.Notification>(),
    ImageAdapter.OnAdapterItemListener,
    DiagnoseDialogSpinner.OnDialogListener {

    private lateinit var adapter: ImageAdapter

    override fun bind(
        viewBinding: FragmentAddEditMedicalRecordBinding,
        viewState: AddEditViewState
    ) {
        viewBinding.datePicker.setDate(viewState.visitDate ?: LocalDate.now())
        viewBinding.textViewCurrentSizeFiles.text = viewState.assets.size.toString()
        viewBinding.buttonAddFile.isEnabled = viewState.canAddMoreFiles()

        adapter.submitList(viewState.assets.map { ImageItemUiModel(it) })

        lifecycleScope.launch {
            viewBinding.spinnerDiagnose.setData(
                viewState.diagnoseSpinnerList.map { DiagnoseItemUiModel(it.id, it.name) }
            )
        }

        viewBinding.spinnerProblemCategory.apply {
            adapter = ArrayAdapter(
                fragmentContext,
                android.R.layout.simple_spinner_item,
                viewState.allProblemCategories.map {
                    it.name
                }
            )
        }
        viewBinding.spinnerMedicalWorker.apply {
            adapter = ArrayAdapter(
                fragmentContext,
                android.R.layout.simple_spinner_item,
                viewState.allMedicalWorkers.map {
                    it.name
                }
            )
        }
    }

    override fun init(
        viewBinding: FragmentAddEditMedicalRecordBinding,
        context: Context,
        lifecycleScope: CoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)

        viewBinding.textViewTotalSizeFiles.text = AddEditViewState.MAX_FILES.toString()
        adapter = viewBinding.recyclerViewFiles.adapter as ImageAdapter
        viewBinding.spinnerDiagnose.setListener(this)
    }

    sealed class Notification {
        object AddFile : Notification()
        data class FileClick(val item: ImageItemUiModel) : Notification()
        data class FileDelete(val item: ImageItemUiModel) : Notification()
        data class DiagnoseSearch(val query: String) : Notification()
        data class DiagnoseClick(val item: DiagnoseItemUiModel) : Notification()
    }

    override fun onItemClicked(item: ImageItemUiModel) {
        notify(Notification.FileClick(item))
    }

    override fun onItemDeleted(item: ImageItemUiModel) {
        notify(Notification.FileDelete(item))
    }

    override fun onDiagnoseClicked(item: DiagnoseItemUiModel): Boolean {
        notify(Notification.DiagnoseClick(item))
        return true
    }

    override fun onDiagnoseSearch(query: String) {
        notify(Notification.DiagnoseSearch(query))
    }
}
