package cz.vvoleman.phr.feature_medicine.ui.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.ext.collectLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.feature_medicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.feature_medicine.presentation.list.viewmodel.ListMedicineViewModel
import cz.vvoleman.phr.feature_medicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.feature_medicine.ui.medicine_selector.MedicineSelector
import cz.vvoleman.phr.feature_medicine.ui.model.list.MedicineUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListMedicineFragment : BaseFragment<ListMedicineViewState, ListMedicineNotification, FragmentListMedicineBinding>() {

    override val viewModel: ListMedicineViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: ListMedicineDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding>

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListMedicineBinding {
        return FragmentListMedicineBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.fabAddMedicalRecord.setOnClickListener {
            viewModel.onCreate()
        }
    }

    override fun handleNotification(notification: ListMedicineNotification) {
        when(notification){
            is ListMedicineNotification.DataLoaded -> {
                Snackbar.make(binding.root, "Data loaded", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}