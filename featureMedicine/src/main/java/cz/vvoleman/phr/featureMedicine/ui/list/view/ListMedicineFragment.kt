package cz.vvoleman.phr.featureMedicine.ui.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.list.viewmodel.ListMedicineViewModel
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.ListMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.nextSchedule.NextSchedule
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListMedicineFragment :
    BaseFragment<ListMedicineViewState, ListMedicineNotification, FragmentListMedicineBinding>(),
    NextSchedule.NextScheduleListener {

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
        binding.nextSchedule.setListener(this)
    }

    override fun handleNotification(notification: ListMedicineNotification) {
        when (notification) {
            is ListMedicineNotification.DataLoaded -> {
                Snackbar.make(binding.root, "Data loaded", Snackbar.LENGTH_SHORT).show()
            }

            is ListMedicineNotification.UnableToLoad -> {
                Snackbar.make(binding.root, "Unable to load data", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onTimeOut() {
        Snackbar.make(binding.root, "Time out", Snackbar.LENGTH_SHORT).show()
    }
}
