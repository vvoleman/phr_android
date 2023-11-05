package cz.vvoleman.phr.featureMedicine.ui.list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.MedicineScheduleUiModel
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.ScheduleItemWithDetailsUiModel

class CatalogueFragment(
    private val listener: CatalogueInterface,
    private val allSchedules: List<MedicineScheduleUiModel>
) : Fragment() {

    private var _binding: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bindin
    }

    interface CatalogueInterface {
        fun onCatalogueItemClick(item: ScheduleItemWithDetailsUiModel)

        fun onCatalogueItemEdit(item: ScheduleItemWithDetailsUiModel)
    }

}