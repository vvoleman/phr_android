package cz.vvoleman.phr.ui.medical_records

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.sidesheet.SideSheetDialog
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.R
import cz.vvoleman.phr.data.OrderRecordsBy
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.medical_records.MedicalRecord
import cz.vvoleman.phr.data.medical_records.MedicalRecordWithDetails
import cz.vvoleman.phr.databinding.FragmentMedicalRecordsBinding
import cz.vvoleman.phr.databinding.SheetRecordsFilterBinding
import cz.vvoleman.phr.util.collectLatestLifecycleFlow
import cz.vvoleman.phr.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class MedicalRecordsFragment : Fragment(R.layout.fragment_medical_records),
    MedicalRecordAdapter.OnItemClickListener, FilterAdapter.FilterAdapterListener {

    private val TAG = "MedicalRecordsFragment"

    private var _binding: FragmentMedicalRecordsBinding? = null
    private val binding get() = _binding!!

    private lateinit var filterSheet: SideSheetDialog
    private val viewModel: MedicalRecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicalRecordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sideSheetBinding = SheetRecordsFilterBinding
            .inflate(LayoutInflater.from(requireContext()), null, false)
        filterSheet = SideSheetDialog(requireContext())
        filterSheet.setContentView(sideSheetBinding.root)
        val topAppBar = filterSheet.findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar?.setNavigationOnClickListener {
            filterSheet.hide()
        }

        val diagnoseAdapter = FilterAdapter(this)
        val facilityAdapter = FilterAdapter(this)
        sideSheetBinding.apply {
            recyclerViewDiagnose.adapter = diagnoseAdapter
            recyclerViewDiagnose.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewFacility.adapter = facilityAdapter
            recyclerViewFacility.layoutManager = LinearLayoutManager(requireContext())
            Log.d(TAG, "sideSheetBinding: $buttonFilter")

            radioGroupSort.setOnCheckedChangeListener{
                _, checkedId ->
                when(checkedId){
                    R.id.by_date -> lifecycleScope.launchWhenCreated { viewModel.filter.changeSortBy(OrderRecordsBy.BY_DATE.name) }
                    R.id.by_facility -> lifecycleScope.launchWhenCreated { viewModel.filter.changeSortBy(OrderRecordsBy.BY_FACILITY.name) }
                }
            }
        }

        val sectionAdapter = SectionAdapter(this)
        binding.apply {
            medicalRecordsRecyclerView.apply {
                adapter = sectionAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            fabAddMedicalRecord.setOnClickListener {
                navigateToAddEdit()
            }
            fabFilterMedicalRecords.setOnClickListener {
                filterSheet.show()
            }
        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        // When medicalRecords changes, update recyclerview
        viewModel.sections.observe(viewLifecycleOwner) {
            Log.d(TAG, "Number of records loaded: ${it.size}")
            sectionAdapter.submitList(it)
            Log.d(TAG, "onViewCreated: ${it.toString()}")
        }

        collectLatestLifecycleFlow(viewModel.filter.sortBy) { sortBy ->
//            Snackbar.make(requireView(), "Sort by $sortBy", Snackbar.LENGTH_SHORT).show()
        }

        collectLatestLifecycleFlow(viewModel.filter.facilities) { list->
            Log.d(TAG, "onViewCreated facility: $list")
            val pairs = list.map { FilterPair(it.id.toString(), it.name, it, true) }
            facilityAdapter.submitList(pairs)
        }

        collectLatestLifecycleFlow(viewModel.filter.diagnoses) {list ->
            Log.d(TAG, "onViewCreated diagnose: $list")
            val pairs = list.map { FilterPair(it.id, it.name, it) }

            diagnoseAdapter.submitList(pairs)
        }

        collectLatestLifecycleFlow(viewModel.filter.sortBy) {
            val id = when(it){
                OrderRecordsBy.BY_DATE -> R.id.by_date
                OrderRecordsBy.BY_FACILITY -> R.id.by_facility
            }
            sideSheetBinding.radioGroupSort.check(id)
        }

        collectLatestLifecycleFlow(viewModel.medicalRecordsEvent) { event ->
            when (event) {
                is MedicalRecordViewModel.MedicalRecordsEvent.ShowUndoDeleteRecordMessage -> {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.medical_recold_deleted),
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(getString(R.string.undo)) {
                            viewModel.onUndoDeleteRecord(event.medicalRecord)
                        }.show()
                }
                is MedicalRecordViewModel.MedicalRecordsEvent.ShowSavedConfirmationMessage -> {
                    Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                }
                is MedicalRecordViewModel.MedicalRecordsEvent.NetworkError -> {
                    Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                }
            }.exhaustive
        }
    }

    override fun onItemClicked(item: MedicalRecordWithDetails) {
        Snackbar.make(
            requireView(),
            "Detail zprávy ještě není implementován",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun navigateToAddEdit(item: MedicalRecord? = null) {
        val action =
            MedicalRecordsFragmentDirections.actionMedicalRecordsFragmentToAddEditMedicalRecord(item)
        findNavController().navigate(action)
    }

    override fun onOptionsMenuClick(record: MedicalRecordWithDetails, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.inflate(R.menu.options_medical_records_popup)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    navigateToAddEdit(record.medicalRecord)
                    true
                }
                R.id.action_delete -> {
                    viewModel.onRecordDeleteClick(record)
                    true
                }
                R.id.action_download -> {
                    Toast.makeText(requireContext(), "Download", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    override fun onOptionCheckChanged(item: FilterPair) {
        Log.d(TAG, "onOptionCheckChanged: $item (${item.checked})")
    }


}