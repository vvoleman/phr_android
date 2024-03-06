package cz.vvoleman.phr.common.ui.fragment.healthcare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.presentation.model.UiState
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.adapter.healthcare.MedicalWorkerAdapter
import cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel.MedicalWorkerViewModel
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalWorkerUiModel
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentMedicalWorkerBinding

class MedicalWorkerFragment :
    Fragment(), MedicalWorkerAdapter.MedicalWorkerAdapterListener {

    private var viewModel: MedicalWorkerViewModel? = null

    private var _binding: FragmentMedicalWorkerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireParentFragment())[MedicalWorkerViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMedicalWorkerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel?.getListener() == null) {
            return
        }

        val adapter = MedicalWorkerAdapter(this)
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

    fun setViewModel(viewModel: MedicalWorkerViewModel) {
        this.viewModel = viewModel
    }

    private fun checkVisibility(condition: Boolean): Int {
        return if (condition) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onItemOptionsMenuClicked(item: MedicalWorkerUiModel, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.inflate(R.menu.options_worker)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    viewModel?.getListener()?.onMedicalWorkerEdit(item)
                    true
                }
                R.id.action_delete -> {
                    viewModel?.getListener()?.onMedicalWorkerDelete(item)
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    override fun onItemClicked(item: MedicalWorkerUiModel) {
        viewModel?.getListener()?.onMedicalWorkerClicked(item)
    }

    interface MedicalWorkerFragmentInterface {
        fun onMedicalWorkerClicked(item: MedicalWorkerUiModel)
        fun onMedicalWorkerDelete(item: MedicalWorkerUiModel)
        fun onMedicalWorkerEdit(item: MedicalWorkerUiModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        const val TAG = "MedicalWorkerFragment"

        fun newInstance(viewModel: MedicalWorkerViewModel): MedicalWorkerFragment {
            val fragment = MedicalWorkerFragment()
            fragment.setViewModel(viewModel)
            return fragment
        }
    }
}
