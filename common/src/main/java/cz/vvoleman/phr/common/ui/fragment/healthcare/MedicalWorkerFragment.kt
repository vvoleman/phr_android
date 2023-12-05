package cz.vvoleman.phr.common.ui.fragment.healthcare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel.MedicalWorkerViewModel
import cz.vvoleman.phr.common_datasource.databinding.FragmentMedicalWorkerBinding

class MedicalWorkerFragment :
    Fragment() {

    private var viewModel: MedicalWorkerViewModel? = null

    private var _binding: FragmentMedicalWorkerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireParentFragment())[MedicalWorkerViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMedicalWorkerBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun setViewModel(viewModel: MedicalWorkerViewModel) {
        this.viewModel = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        const val TAG = "MedicalWorkerFragment"

        fun newInstance(viewModel: MedicalWorkerViewModel): MedicalWorkerFragment {
            val fragment = MedicalWorkerFragment()
            fragment.viewModel = viewModel
            return fragment
        }
    }
}
