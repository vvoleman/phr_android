package cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.fragment.AbstractTimelineFragment
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common.utils.checkVisibility
import cz.vvoleman.phr.common.utils.withLeadingZero
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentMeasurementTimelineBinding
import cz.vvoleman.phr.featureMeasurement.ui.adapter.MeasurementTimelineAdapter
import cz.vvoleman.phr.featureMeasurement.ui.model.core.ScheduledMeasurementGroupUiModel
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel.MeasurementTimelineViewModel

class MeasurementTimelineFragment : AbstractTimelineFragment<ScheduledMeasurementGroupUiModel>() {

    private var viewModel: MeasurementTimelineViewModel? = null
    private var _binding: FragmentMeasurementTimelineBinding? = null
    private val binding get() = _binding!!
    private var isMultipleDays = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMeasurementTimelineBinding.inflate(inflater, container, false)
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
                val data = it.data
                isMultipleDays = isMultipleDays(data)
                groupAdapter.submitList(it.data)
            }
        }
    }

    fun setViewModel(viewModel: MeasurementTimelineViewModel) {
        this.viewModel = viewModel
    }

    override fun bindGroupedItems(
        groupBinding: ItemGroupedItemsBinding,
        item: GroupedItemsUiModel<ScheduledMeasurementGroupUiModel>
    ) {
        val adapter = MeasurementTimelineAdapter(viewModel!!.getListener()!!)

        val dateTime = getDateFromValue(item.value.toString())
        var text = if (isMultipleDays) {
            "${dateTime.dayOfMonth}. ${dateTime.monthValue}. - ${dateTime.plusDays(
                item.items.size.toLong() - 1
            ).dayOfMonth}. ${dateTime.plusDays(item.items.size.toLong() - 1).monthValue}."
        } else {
            ""
        }
        text += "${dateTime.hour.withLeadingZero()}:${dateTime.minute.withLeadingZero()}"

        groupBinding.apply {
            textViewTitle.text = text
            recyclerView.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }

        adapter.submitList(item.items)
    }

    override fun onDestroyGroupedItems(groupBinding: ItemGroupedItemsBinding) {
        groupBinding.recyclerView.adapter = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        const val TIMESTAMP_PARTS = 4
        const val GROUP_STRING_PARTS = 3
        fun newInstance(viewModel: MeasurementTimelineViewModel): MeasurementTimelineFragment {
            val fragment = MeasurementTimelineFragment()
            fragment.setViewModel(viewModel)
            return fragment
        }
    }
}
