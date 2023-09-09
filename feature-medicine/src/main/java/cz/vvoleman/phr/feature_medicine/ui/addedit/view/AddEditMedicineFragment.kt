package cz.vvoleman.phr.feature_medicine.ui.addedit.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.feature_medicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.feature_medicine.presentation.addedit.model.AddEditMedicineNotification
import cz.vvoleman.phr.feature_medicine.presentation.addedit.model.AddEditMedicineViewState
import cz.vvoleman.phr.feature_medicine.presentation.addedit.viewmodel.AddEditMedicineViewModel
import cz.vvoleman.phr.feature_medicine.ui.addedit.mapper.AddEditMedicineDestinationUiMapper
import cz.vvoleman.phr.feature_medicine.ui.medicine_selector.MedicineSelector
import cz.vvoleman.phr.feature_medicine.ui.model.list.MedicineUiModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMedicineFragment :
    BaseFragment<AddEditMedicineViewState, AddEditMedicineNotification, FragmentAddEditMedicineBinding>(),
    MedicineSelector.MedicineSelectorListener {

    override val viewModel: AddEditMedicineViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditMedicineDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding>

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddEditMedicineBinding {
        return FragmentAddEditMedicineBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.medicineSelector.setListener(this)
    }

    override fun handleNotification(notification: AddEditMedicineNotification) {
        when (notification) {
            is AddEditMedicineNotification.DataLoaded -> {
                Snackbar.make(binding.root, "Data loaded", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMedicineSelected(medicine: MedicineUiModel?) {
        Snackbar.make(binding.root, "Medicine selected", Snackbar.LENGTH_SHORT).show()
    }

    override fun onMedicineSelectorSearch(query: String) {
        viewModel.onSearch(query)
    }
}
