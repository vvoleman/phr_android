package cz.vvoleman.phr.feature_medicine.ui.time_selector

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.vvoleman.phr.feature_medicine.databinding.ViewTimeSelectorBinding

class TimeSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewTimeSelectorBinding
    private val times: MutableList<TimeUiModel> = mutableListOf()

    init {
        binding = ViewTimeSelectorBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTimes(times: List<TimeUiModel>) {
        this.times.clear()
        this.times.addAll(times)
        val timeAdapter = TimeAdapter()
        timeAdapter.submitList(times)

        binding.recyclerViewTimes.apply {
            adapter = timeAdapter
            setHasFixedSize(true)
        }
    }
}
