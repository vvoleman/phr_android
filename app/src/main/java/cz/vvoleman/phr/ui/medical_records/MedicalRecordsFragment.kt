package cz.vvoleman.phr.ui.medical_records

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
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
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.R
import cz.vvoleman.phr.data.medical_records.MedicalRecord
import cz.vvoleman.phr.data.medical_records.MedicalRecordWithDetails
import cz.vvoleman.phr.databinding.FragmentMedicalRecordsBinding
import cz.vvoleman.phr.util.exhaustive
import cz.vvoleman.phr.util.getNameOfDay
import cz.vvoleman.phr.util.getNameOfMonth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class MedicalRecordsFragment : Fragment(R.layout.fragment_medical_records),
    MedicalRecordAdapter.OnItemClickListener {

    private val TAG = "MedicalRecordsFragment"

    private var _binding: FragmentMedicalRecordsBinding? = null
    private val binding get() = _binding!!

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

        val medicalRecordAdapter = MedicalRecordAdapter(this)

        Log.d(TAG, "Number of records loaded: ${medicalRecordAdapter.itemCount}")

        binding.apply {
            medicalRecordsRecyclerView.apply {
                adapter = medicalRecordAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            fabAddMedicalRecord.setOnClickListener {
                navigateToAddEdit()
            }
        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        // When medicalRecords changes, update recyclerview
        viewModel.medicalRecords.observe(viewLifecycleOwner) {
            medicalRecordAdapter.submitList(it)
            Log.d(TAG, "onViewCreated: ${it.toString()}")
        }

        // Handle events from view model
        handleViewModelEvents()
    }

    private fun handleViewModelEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.medicalRecordsEvent.collect { event ->
                when(event) {
                    is MedicalRecordViewModel.MedicalRecordsEvent.ShowUndoDeleteRecordMessage -> {
                        Snackbar.make(requireView(), getString(R.string.medical_recold_deleted), Snackbar.LENGTH_LONG)
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
    }

    override fun onItemClicked(item: MedicalRecordWithDetails) {
        Snackbar.make(requireView(), "Detail zprávy ještě není implementován", Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToAddEdit(item: MedicalRecord? = null) {
        val action = MedicalRecordsFragmentDirections.actionMedicalRecordsFragmentToAddEditMedicalRecord(item)
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



}