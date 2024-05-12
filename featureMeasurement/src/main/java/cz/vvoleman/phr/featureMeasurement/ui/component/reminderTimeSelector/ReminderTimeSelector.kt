package cz.vvoleman.phr.featureMeasurement.ui.component.reminderTimeSelector

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import cz.vvoleman.phr.featureMeasurement.databinding.ViewTimeSelectorBinding
import java.time.LocalTime

class ReminderTimeSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), TimeAdapter.TimeAdapterListener {

    private val binding: ViewTimeSelectorBinding
    private val times: MutableSet<LocalTime> = mutableSetOf()
    private var _adapter: TimeAdapter? = null

    private var _listener: TimeSelectorListener? = null

    init {
        binding = ViewTimeSelectorBinding.inflate(LayoutInflater.from(context), this, true)
        _adapter = TimeAdapter(this)
    }

    fun setTimes(times: Set<LocalTime>) {
        this.times.clear()
        this.times.addAll(times)
        _adapter?.submitList(times.toList())

        binding.recyclerViewTimes.apply {
            val layout = layoutManager as GridLayoutManager
            layout.spanCount = if (times.isNotEmpty()) times.size else 1
            layout.orientation = GridLayoutManager.VERTICAL
            layoutManager = layout
            adapter = _adapter
        }
    }

    fun setListener(listener: TimeSelectorListener) {
        _listener = listener
    }

    fun changeTime(index: Int, newTime: LocalTime) {
        times.remove(times.elementAt(index))
        times.add(newTime)
        orderByTime()
    }

    fun onTimeRemove(index: Int) {
        times.remove(times.elementAt(index))
        orderByTime()
    }

    fun timeAdd(time: LocalTime) {
        times.add(time)
        orderByTime()
    }

    private fun orderByTime() {
        times.sortedBy { it }
        _adapter?.submitList(times.toList())
    }

    override fun onTimeClick(index: Int, anchorView: View) {
        _listener?.onTimeClick(index, anchorView)
    }

    interface TimeSelectorListener {
        fun onTimeClick(index: Int, anchorView: View)
    }
}
