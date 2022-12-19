package cz.vvoleman.phr.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.vvoleman.phr.R
import cz.vvoleman.phr.databinding.FragmentMedicineBinding
import cz.vvoleman.phr.viewmodels.MedicineViewModelFactory

class MedicineFragment : Fragment(R.layout.fragment_medicine) {

    private var _binding: FragmentMedicineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}