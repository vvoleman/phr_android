package cz.vvoleman.phr.featureMedicine.ui.timeSelector

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import cz.vvoleman.phr.featureMedicine.databinding.ViewTimeSelectorBinding
import java.time.LocalTime

class TimeSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), TimeAdapter.TimeAdapterListener {

    private val binding: ViewTimeSelectorBinding
    private val times: MutableList<TimeUiModel> = mutableListOf()
    private var _adapter: TimeAdapter? = null

    private var _listener: TimeSelectorListener? = null

    init {
        binding = ViewTimeSelectorBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTimes(times: List<TimeUiModel>) {
        this.times.clear()
        this.times.addAll(times)
        _adapter = TimeAdapter(this)
        _adapter?.submitList(times)

        binding.recyclerViewTimes.apply {
            adapter = _adapter
            setHasFixedSize(true)
        }
    }

    fun setListener(listener: TimeSelectorListener) {
        _listener = listener
    }

    fun changeTime(index: Int, newTime: LocalTime) {
        times[index] = times[index].copy(time = newTime)
    }

    fun onTimeRemove(index: Int) {
        times.removeAt(index)
        orderByTime()
    }

    fun timeAdd(time: LocalTime) {
        times.add(TimeUiModel(time, 1))
        orderByTime()
    }

    private fun orderByTime() {
        times.sortBy { it.time }
        _adapter?.submitList(times)
    }

    override fun onTimeClick(index: Int, anchorView: View) {
        _listener?.onTimeClick(index, anchorView)
    }

    interface TimeSelectorListener {
        fun onTimeClick(index: Int, anchorView: View)
    }
}
