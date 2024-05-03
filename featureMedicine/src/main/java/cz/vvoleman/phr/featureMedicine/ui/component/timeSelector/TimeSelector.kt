package cz.vvoleman.phr.featureMedicine.ui.component.timeSelector

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import cz.vvoleman.phr.featureMedicine.databinding.ViewTimeSelectorBinding
import java.time.LocalTime

class TimeSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), TimeAdapter.TimeAdapterListener {

    private val binding: ViewTimeSelectorBinding
    private val times: MutableList<TimeUiModel> = mutableListOf()
    private var _lifecycleScope: LifecycleCoroutineScope? = null
    private var _adapter: TimeAdapter? = null

    private var _listener: TimeSelectorListener? = null

    init {
        binding = ViewTimeSelectorBinding.inflate(LayoutInflater.from(context), this, true)
        Log.d(TAG, "init: ")
    }

    fun setLifecycleScope(lifecycleScope: LifecycleCoroutineScope) {
        _lifecycleScope = lifecycleScope
    }

    fun setTimes(times: List<TimeUiModel>) {
        this.times.clear()
        this.times.addAll(times)
        val timeAdapter = getAdapter()
        timeAdapter.submitList(times)

        Log.d(TAG, "calling setTimes")

        Log.d(TAG, "spanCount ${if (times.isNotEmpty()) times.size else 1}")
        binding.recyclerViewTimes.apply {
            val layout = layoutManager as GridLayoutManager
            layout.spanCount = if (times.isNotEmpty()) times.size else 1
            layout.orientation = GridLayoutManager.VERTICAL
            layoutManager = layout
            adapter = timeAdapter
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
        times.add(TimeUiModel(null, time, 1))
        orderByTime()
    }

    private fun orderByTime() {
        times.sortBy { it.time }
        getAdapter().submitList(times)
    }

    override fun onTimeClick(index: Int, anchorView: View) {
        _listener?.onTimeClick(index, anchorView)
    }

    override fun onQuantityChange(index: Int, newValue: Number) {
        Log.d(TAG, "onQuantityChange: $newValue")
        _listener?.onQuantityChange(index, newValue)
    }

    private fun getAdapter(): TimeAdapter {
        if (_adapter == null) {
            _adapter = TimeAdapter(_lifecycleScope!!, this)
        }

        return _adapter!!
    }

    interface TimeSelectorListener {
        fun onTimeClick(index: Int, anchorView: View)
        fun onQuantityChange(index: Int, newValue: Number)
    }

    companion object {
        private const val TAG = "TimeSelector"
    }
}
