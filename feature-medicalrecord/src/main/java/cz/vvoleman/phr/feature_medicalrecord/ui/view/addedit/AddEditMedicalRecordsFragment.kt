package cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.ext.collectLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.feature_medicalrecord.R
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.viewmodel.AddEditViewModel
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.AddEditDestinationUiMapper
import cz.vvoleman.phr.feature_medicalrecord.ui.model.ImageItemUiModel
import cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit.binder.AddEditBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMedicalRecordsFragment : BaseFragment<
        AddEditViewState,
        AddEditNotification,
        FragmentAddEditMedicalRecordBinding>(), ImageAdapter.OnAdapterItemListener {

    override val viewModel: AddEditViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder:
            ViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding>

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddEditMedicalRecordBinding {
        return FragmentAddEditMedicalRecordBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.buttonAddFile.setOnClickListener {
            viewModel.onAddNewFile()
        }

        val addEditBinder = (viewStateBinder as AddEditBinder)
        collectLifecycleFlow(addEditBinder.notification) {
            when (it) {
                is AddEditBinder.Notification.AddFile -> TODO()
                is AddEditBinder.Notification.FileClick -> TODO()
                is AddEditBinder.Notification.FileDelete -> {
                    viewModel.onDeleteFile(it.item.uri)
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
        }
    }

    override fun onItemDeleted(item: ImageItemUiModel) {
        viewModel.onDeleteFile(item.uri)
    }

    override fun onItemClicked(item: ImageItemUiModel) {
        TODO("Not yet implemented")
    }
}