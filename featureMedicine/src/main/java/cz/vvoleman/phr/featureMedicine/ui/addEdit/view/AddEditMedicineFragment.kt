package cz.vvoleman.phr.featureMedicine.ui.addEdit.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.viewmodel.AddEditMedicineViewModel
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.AddEditMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.medicineSelector.MedicineSelector
import cz.vvoleman.phr.featureMedicine.ui.model.list.MedicineUiModel
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
