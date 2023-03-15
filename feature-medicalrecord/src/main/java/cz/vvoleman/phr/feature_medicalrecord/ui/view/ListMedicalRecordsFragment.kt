package cz.vvoleman.phr.feature_medicalrecord.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.ui.adapter.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.adapter.OnAdapterItemListener
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.viewmodel.ListMedicalRecordsViewModel
import cz.vvoleman.phr.feature_medicalrecord.ui.mapper.MedicalRecordDomainModelToUiMapper
import cz.vvoleman.phr.feature_medicalrecord.ui.model.MedicalRecordUiModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListMedicalRecordsFragment : BaseFragment<
        ListMedicalRecordsViewState,
        ListMedicalRecordsNotification,
        FragmentListMedicalRecordsBinding>(), OnAdapterItemListener<MedicalRecordUiModel> {

    override val viewModel: ListMedicalRecordsViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: DestinationUiMapper

    @Inject
    override lateinit var viewStateBinder:
            ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding>

    @Inject
    lateinit var medicalRecordDomainModelToUiMapper: MedicalRecordDomainModelToUiMapper

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

        val listAdapter = GroupedItemsAdapter(this)
        binding.recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        viewStateBinder.addAdapter(listAdapter)
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

    override fun onItemClicked(item: MedicalRecordUiModel) {
        TODO("Not yet implemented")
    }

    override fun bind(
        binding: ItemGroupedItemsBinding,
        item: GroupedItemsUiModel<MedicalRecordUiModel>
    ) {
        binding.apply {  }
    }


}