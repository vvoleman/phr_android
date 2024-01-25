package cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common.utils.checkVisibility
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentMeasurementGroupBinding
import cz.vvoleman.phr.featureMeasurement.ui.adapter.list.MeasurementGroupAdapter
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupUiModel
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel.MeasurementGroupViewModel

class MeasurementGroupFragment : Fragment(), GroupedItemsAdapter.GroupedItemsAdapterInterface<MeasurementGroupUiModel> {

    private var viewModel: MeasurementGroupViewModel? = null
    private var _binding: FragmentMeasurementGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMeasurementGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel?.getListener() == null) {
            return
        }

        val groupAdapter = GroupedItemsAdapter(this)
        binding.recyclerView.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        collectLatestLifecycleFlow(viewModel!!.items) {
            binding.textViewEmpty.visibility = checkVisibility(it is UiState.Success && it.data.isEmpty())
            binding.recyclerView.visibility = checkVisibility(it is UiState.Success && it.data.isNotEmpty())
            binding.progressBar.visibility = checkVisibility(it is UiState.Loading)

            if (it is UiState.Success) {
                groupAdapter.submitList(it.data)
            }
        }
    }

    fun setViewModel(viewModel: MeasurementGroupViewModel) {
        this.viewModel = viewModel
    }

    override fun bindGroupedItems(
        groupBinding: ItemGroupedItemsBinding,
        item: GroupedItemsUiModel<MeasurementGroupUiModel>
    ) {
        val measurementGroupAdapter = MeasurementGroupAdapter(viewModel!!.getListener()!!)

        groupBinding.apply {
            recyclerView.apply {
                adapter = measurementGroupAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }

            measurementGroupAdapter.submitList(item.items)
            textViewTitle.text = item.value.toString()
        }
    }

    override fun onDestroyGroupedItems(groupBinding: ItemGroupedItemsBinding) {
        groupBinding.recyclerView.adapter = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        fun newInstance(viewModel: MeasurementGroupViewModel): MeasurementGroupFragment {
            val fragment = MeasurementGroupFragment()
            fragment.setViewModel(viewModel)
            return fragment
        }
    }

}
