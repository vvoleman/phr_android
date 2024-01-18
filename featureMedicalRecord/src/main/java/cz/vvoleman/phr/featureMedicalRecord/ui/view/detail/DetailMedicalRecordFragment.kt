package cz.vvoleman.phr.featureMedicalRecord.ui.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentDetailMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel.DetailMedicalRecordViewModel
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination.DetailMedicalRecordDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMedicalRecordFragment :
    BaseFragment<DetailMedicalRecordViewState, DetailMedicalRecordNotification, FragmentDetailMedicalRecordBinding>() {

    override val viewModel: DetailMedicalRecordViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DetailMedicalRecordDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<DetailMedicalRecordViewState, FragmentDetailMedicalRecordBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailMedicalRecordBinding {
        return FragmentDetailMedicalRecordBinding.inflate(inflater, container, false)
    }

    override fun handleNotification(notification: DetailMedicalRecordNotification) {
        when (notification) {
            else -> {
            }
        }
    }
}
