package cz.vvoleman.phr.common.ui.view.addedit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.addedit.AddEditPatientNotification
import cz.vvoleman.phr.common.presentation.model.addedit.AddEditViewState
import cz.vvoleman.phr.common.presentation.viewmodel.AddEditPatientViewModel
import cz.vvoleman.phr.common.ui.mapper.destination.AddEditPatientDestinationUiMapper
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditPatientBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditPatientFragment :
    BaseFragment<AddEditViewState, AddEditPatientNotification, FragmentAddEditPatientBinding>() {

    override val viewModel: AddEditPatientViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditPatientDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditViewState, FragmentAddEditPatientBinding>

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddEditPatientBinding {
        return FragmentAddEditPatientBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.buttonSave.setOnClickListener {
            val nameValue = binding.textFieldName.text.toString()
            val dateValue = binding.datePicker.getDate()
            viewModel.onSave(nameValue, dateValue)
        }
    }

    override fun handleNotification(notification: AddEditPatientNotification) {
        when (notification) {
            is AddEditPatientNotification.Error -> {
                Snackbar.make(binding.root, getText(R.string.addedit_error), Snackbar.LENGTH_LONG)
                    .show()
            }
            is AddEditPatientNotification.PatientSaved -> {
                Snackbar.make(
                    binding.root,
                    String.format(
                        getText(R.string.addedit_saved).toString(),
                        notification.patient.name
                    ),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}