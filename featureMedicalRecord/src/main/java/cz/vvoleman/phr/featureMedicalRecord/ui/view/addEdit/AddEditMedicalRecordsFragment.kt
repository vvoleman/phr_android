package cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.map
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.ext.collectLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.view.datepicker.DatePicker
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel.AddEditMedicalRecordViewModel
import cz.vvoleman.phr.featureMedicalRecord.ui.component.diagnoseSelector.DiagnoseSelector
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination.AddEditDestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.DiagnoseUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.model.DiagnoseUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.model.ImageItemUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter.ImageAdapter
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.binder.AddEditBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMedicalRecordsFragment :
    BaseFragment<
            AddEditViewState,
            AddEditNotification,
            FragmentAddEditMedicalRecordBinding
            >(),
    ImageAdapter.OnAdapterItemListener,
    DatePicker.DatePickerListener,
    DiagnoseSelector.DiagnoseSelectorListener {

    override val viewModel: AddEditMedicalRecordViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder:
            ViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding>

    @Inject
    lateinit var diagnoseMapper: DiagnoseUiModelToPresentationMapper

    @Inject
    lateinit var specificWorkerMapper: SpecificMedicalWorkerUiModelToPresentationMapper

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddEditMedicalRecordBinding {
        val binding = FragmentAddEditMedicalRecordBinding.inflate(inflater, container, false)

        val filesAdapter = ImageAdapter(this)
        binding.recyclerViewFiles.apply {
            adapter = filesAdapter
            setHasFixedSize(false)
            visibility = View.VISIBLE
        }

        return binding
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.buttonAddFile.setOnClickListener {
            viewModel.onAddNewFile()
        }

        binding.buttonSave.setOnClickListener {
            lifecycleScope.launch { viewModel.onSubmit() }
        }

        binding.diagnoseSelector.setListener(this)

        binding.datePicker.setListener(this)
        val addEditBinder = (viewStateBinder as AddEditBinder)
        collectLifecycleFlow(addEditBinder.notification) {
            when (it) {
                is AddEditBinder.Notification.AddFile -> TODO()
                is AddEditBinder.Notification.FileClick -> {
                    onItemClicked(it.item)
                }

                is AddEditBinder.Notification.FileDelete -> {
                    onItemDeleted(it.item)
                }

                is AddEditBinder.Notification.ProblemCategorySelected -> {
                    viewModel.onProblemCategorySelected(it.value)
                }

                is AddEditBinder.Notification.MedicalWorkerSelected -> {
                    val model = specificWorkerMapper.toPresentation(it.item)
                    viewModel.onMedicalWorkerSelected(model)
                }
            }
        }
    }

    override fun handleNotification(notification: AddEditNotification) {
        when (notification) {
            is AddEditNotification.LimitFilesReached -> {
                Snackbar.make(
                    binding.root,
                    getText(R.string.add_eddit_medical_record_file_limit_reached),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            AddEditNotification.MissingData -> {
                Snackbar.make(binding.root, "Missing data", Snackbar.LENGTH_SHORT).show()
            }

            AddEditNotification.Error ->
                Snackbar.make(binding.root, getText(R.string.add_edit_error), Snackbar.LENGTH_SHORT)
                    .show()

            AddEditNotification.PatientNotSelected -> {
                Snackbar.make(
                    binding.root,
                    getText(R.string.add_edit_patient_not_selected),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            AddEditNotification.Success ->
                Snackbar.make(
                    binding.root,
                    getText(R.string.add_edit_success),
                    Snackbar.LENGTH_SHORT
                ).show()
        }
    }

    override fun onDateSelected(date: LocalDate) {
        viewModel.onDateSelected(date)
    }

    override fun onItemDeleted(item: ImageItemUiModel) {
        Log.d(TAG, "onItemDeleted: $item")
        viewModel.onDeleteFile(item.asset)
    }

    override fun onItemClicked(item: ImageItemUiModel) {
        Log.d(TAG, "onItemClicked: $item")
    }

    override fun onDiagnoseSelected(diagnose: DiagnoseUiModel?, position: Int?) {
        val model = diagnose?.let {diagnoseMapper.toPresentation(it)}
        viewModel.onDiagnoseSelected(model)
    }

    override fun onDiagnoseSelectorSearch(
        query: String,
        callback: suspend (PagingData<DiagnoseUiModel>) -> Unit
    ) {
        val stream = viewModel.onDiagnoseSearch(query)

        lifecycleScope.launch {
            stream.map { pagingData ->
                pagingData.map {diagnoseMapper.toUi(it)}
            }.collectLatest { pagingData ->
                callback(pagingData)
            }
        }
    }
}
