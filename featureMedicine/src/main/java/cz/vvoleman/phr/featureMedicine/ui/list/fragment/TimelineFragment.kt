package cz.vvoleman.phr.featureMedicine.ui.list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.ui.model.list.schedule.ScheduleItemWithDetailsUiModel

class TimelineFragment(
    private val listener: TimelineInterface,
    private val nextSchedules: List<ScheduleItemWithDetailsUiModel>
) : Fragment() {

    private var _binding: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    interface TimelineInterface {
        fun onTimelineItemClick(item: ScheduleItemWithDetailsUiModel)

        fun onTimelineItemAlarmToggle(item: ScheduleItemWithDetailsUiModel, oldState: Boolean)
    }

}