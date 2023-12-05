package cz.vvoleman.phr.common.ui.adapter.healthcare

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.vvoleman.phr.common.ui.fragment.healthcare.MedicalFacilityFragment
import cz.vvoleman.phr.common.ui.fragment.healthcare.MedicalWorkerFragment
import cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel.MedicalFacilityViewModel
import cz.vvoleman.phr.common.ui.fragment.healthcare.viewmodel.MedicalWorkerViewModel

class HealthcareFragmentAdapter(
    private val medicalWorkerViewModel: MedicalWorkerViewModel,
    private val medicalFacilityViewModel: MedicalFacilityViewModel,
    parent: Fragment,
) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MedicalWorkerFragment.newInstance(medicalWorkerViewModel)
            1 -> MedicalFacilityFragment.newInstance(medicalFacilityViewModel)
            else -> {
                error("Invalid fragment adapter position")
            }
        }
    }
}
