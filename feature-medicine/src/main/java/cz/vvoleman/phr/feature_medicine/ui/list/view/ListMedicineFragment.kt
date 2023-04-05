package cz.vvoleman.phr.feature_medicine.ui.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.feature_medicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineNotification
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.feature_medicine.presentation.list.viewmodel.ListMedicineViewModel
import cz.vvoleman.phr.feature_medicine.ui.list.mapper.ListMedicineDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
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

        binding.buttonSearch.setOnClickListener {
            val text = binding.editTextQuery.text.toString()
            viewModel.onSearch(text)
        }
    }

    override fun handleNotification(notification: ListMedicineNotification) {
        TODO("Not yet implemented")
    }
}