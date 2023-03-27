package cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.viewmodel.AddEditViewModel
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.AddEditDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMedicalRecordsFragment : BaseFragment<
        AddEditViewState,
        AddEditNotification,
        FragmentAddEditMedicalRecordBinding>() {

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
    }

    override fun handleNotification(notification: AddEditNotification) {
        Log.d("AddEditMedicalRecordsFragment", "handleNotification: $notification")
    }


}