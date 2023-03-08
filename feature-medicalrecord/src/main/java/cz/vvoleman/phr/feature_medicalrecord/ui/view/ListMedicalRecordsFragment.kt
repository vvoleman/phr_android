package cz.vvoleman.phr.feature_medicalrecord.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.viewmodel.ListMedicalRecordsViewModel

class ListMedicalRecordsFragment : BaseFragment<ListMedicalRecordsViewState, ListMedicalRecordsNotification>() {

    override val viewModel: ListMedicalRecordsViewModel by viewModels()

    override val destinationMapper: DestinationUiMapper
        get() = TODO("Not yet implemented")
    override val viewStateBinders: List<ViewStateBinder<ListMedicalRecordsViewState, ViewBinding>>
        get() = TODO("Not yet implemented")

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return FragmentListMedicalRecordsBinding.inflate(inflater, container, false)
    }
}