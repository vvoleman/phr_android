package cz.vvoleman.phr.featureMedicine.ui.list.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.common.ui.adapter.grouped.GroupedItemsAdapter
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.common_datasource.databinding.ItemGroupedItemsBinding
import cz.vvoleman.phr.featureMedicine.databinding.FragmentMedicineCatalogueBinding
import cz.vvoleman.phr.featureMedicine.ui.list.adapter.MedicineCatalogueAdapter
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.MedicineScheduleUiModel

class MedicineCatalogueFragment(
    private val listener: MedicineScheduleInterface,
    private val allSchedules: List<GroupedItemsUiModel<MedicineScheduleUiModel>>
) : Fragment(), GroupedItemsAdapter.GroupedItemsAdapterInterface<MedicineScheduleUiModel>,
    MedicineCatalogueAdapter.MedicineCatalogueAdapterInterface {

    private var _binding: FragmentMedicineCatalogueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMedicineCatalogueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (allSchedules.isEmpty()) {
            binding.textViewEmpty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            return
        }

        val groupAdapter = GroupedItemsAdapter(this)
        binding.recyclerView.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        groupAdapter.submitList(allSchedules)

        Log.d("MedicineCatalogueFragment", "onViewCreated: ${allSchedules.size}")
    }

    override fun bind(binding: ItemGroupedItemsBinding, item: GroupedItemsUiModel<MedicineScheduleUiModel>) {
        val medicineCatalogueAdapter = MedicineCatalogueAdapter(this)

        binding.apply {
            recyclerView.apply {
                adapter = medicineCatalogueAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }

            medicineCatalogueAdapter.submitList(item.items)
            textViewTitle.text = item.value.toString()
        }

        medicineCatalogueAdapter.submitList(item.items)
    }

    override fun onCatalogueItemClick(item: MedicineScheduleUiModel) {
        listener.onCatalogueItemClick(item)
    }

    override fun onCatalogueItemEdit(item: MedicineScheduleUiModel) {
        listener.onCatalogueItemEdit(item)
    }

    interface MedicineScheduleInterface {
        fun onCatalogueItemClick(item: MedicineScheduleUiModel)

        fun onCatalogueItemEdit(item: MedicineScheduleUiModel)
    }

}