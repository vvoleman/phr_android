package cz.vvoleman.phr.common.ui.fragment.healthcare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel.MedicalFacilityViewModel
import cz.vvoleman.phr.common_datasource.databinding.FragmentMedicalFacilityBinding

class MedicalFacilityFragment : Fragment() {

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

    fun setViewModel(viewModel: MedicalFacilityViewModel) {
        this.viewModel = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        const val TAG = "MedicalFacilityFragment"

        fun newInstance(viewModel: MedicalFacilityViewModel): MedicalFacilityFragment {
            val fragment = MedicalFacilityFragment()
            fragment.setViewModel(viewModel)
            return fragment
        }
    }
}
