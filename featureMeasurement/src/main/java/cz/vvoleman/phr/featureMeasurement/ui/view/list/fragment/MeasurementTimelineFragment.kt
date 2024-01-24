package cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentMeasurementTimelineBinding
import cz.vvoleman.phr.featureMeasurement.ui.view.list.fragment.viewModel.TimelineViewModel

class MeasurementTimelineFragment : Fragment() {

    private var viewModel: TimelineViewModel? = null
    private var _binding: FragmentMeasurementTimelineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMeasurementTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setViewModel(viewModel: TimelineViewModel) {
        this.viewModel = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        fun newInstance(viewModel: TimelineViewModel): MeasurementTimelineFragment {
            val fragment = MeasurementTimelineFragment()
            fragment.setViewModel(viewModel)
            return fragment
        }
    }

}
