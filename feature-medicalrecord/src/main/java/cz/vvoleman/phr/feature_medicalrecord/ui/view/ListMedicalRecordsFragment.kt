package cz.vvoleman.phr.feature_medicalrecord.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.viewmodel.ListMedicalRecordsViewModel
import cz.vvoleman.phr.feature_medicalrecord.ui.view.binder.ButtonBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListMedicalRecordsFragment : BaseFragment<
        ListMedicalRecordsViewState,
        ListMedicalRecordsNotification,
        FragmentListMedicalRecordsBinding>() {

    override val viewModel: ListMedicalRecordsViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DestinationUiMapper

    @Inject
    override lateinit var viewStateBinder:
            ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding>

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListMedicalRecordsBinding {
        return FragmentListMedicalRecordsBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        binding.btn.setOnClickListener {
            viewModel.onSelect()
        }
    }

    override fun handleNotification(notification: ListMedicalRecordsNotification) {
        when (notification) {
            is ListMedicalRecordsNotification.Success -> {
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
            }
            is ListMedicalRecordsNotification.NotFoundError -> {
                Snackbar.make(binding.root, "Not found", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}