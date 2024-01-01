package cz.vvoleman.phr.common.ui.fragment.healthcare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.adapter.healthcare.MedicalFacilityAdapter
import cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel.MedicalFacilityViewModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common_datasource.databinding.FragmentMedicalFacilityBinding

class MedicalFacilityFragment : Fragment(), MedicalFacilityAdapter.MedicalFacilityAdapterListener {

    private var viewModel: MedicalFacilityViewModel? = null

    private var _binding: FragmentMedicalFacilityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireParentFragment())[MedicalFacilityViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMedicalFacilityBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel?.getListener() == null) {
            return
        }

        val adapter = MedicalFacilityAdapter(this)
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
        }

        collectLatestLifecycleFlow(viewModel!!.items) {
            binding.textViewEmpty.visibility = checkVisibility(it is UiState.Success && it.data.isEmpty())
            binding.recyclerView.visibility = checkVisibility(it is UiState.Success && it.data.isNotEmpty())
            binding.progressBar.visibility = checkVisibility(it is UiState.Loading)

            if (it is UiState.Success) {
                adapter.submitList(it.data)
            }
        }
    }

    private fun checkVisibility(condition: Boolean): Int {
        return if (condition) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun setViewModel(viewModel: MedicalFacilityViewModel) {
        this.viewModel = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    interface MedicalFacilityFragmentInterface {
        fun onMedicalFacilityClick(item: MedicalFacilityUiModel)
    }

    companion object {
        const val TAG = "MedicalFacilityFragment"

        fun newInstance(viewModel: MedicalFacilityViewModel): MedicalFacilityFragment {
            val fragment = MedicalFacilityFragment()
            fragment.setViewModel(viewModel)
            return fragment
        }
    }

    override fun onItemClick(item: MedicalFacilityUiModel) {
        viewModel?.getListener()?.onMedicalFacilityClick(item)
    }
}
