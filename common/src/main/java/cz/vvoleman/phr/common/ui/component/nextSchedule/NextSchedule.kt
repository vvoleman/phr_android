package cz.vvoleman.phr.common.ui.component.nextSchedule

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.common.ui.component.timeLeft.TimeLeft
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.ViewNextScheduleBinding
import java.time.LocalDateTime

class NextSchedule @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), TimeLeft.TimeLeftListener {

    private val binding: ViewNextScheduleBinding
    private var _adapter: NextScheduleAdapter? = null

    private var _listener: NextScheduleListener? = null
    private var _schedule: NextScheduleUiModel? = null
    private var _labelText: String? = null
    private var _multipleItemsText: String? = null
    private var _noScheduleText: String? = null

    init {
        Log.d("NextSchedule", "1")
        binding = ViewNextScheduleBinding.inflate(LayoutInflater.from(context), this, true)

        // Load attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.NextSchedule,
            0,
            0
        ).apply {

            try {
                _labelText = getString(R.styleable.NextSchedule_labelText)
                _multipleItemsText = getString(R.styleable.NextSchedule_multipleItemsText)
                _noScheduleText = getString(R.styleable.NextSchedule_noSchedulteText)
            } finally {
                recycle()
            }
        }

        _adapter = NextScheduleAdapter()
        binding.recyclerView.apply {
            adapter = _adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        binding.root.setOnClickListener {
            _schedule?.let {
                _listener?.onNextScheduleClick(it)
            }
        }

        binding.timeLeft.setListener(this)

        render()
    }

    fun setSchedule(schedule: NextScheduleUiModel?) {
        _schedule = schedule

        val items = schedule?.items ?: emptyList()

        _adapter?.submitList(items)
        render()
    }

    fun setListener(listener: NextScheduleListener) {
        _listener = listener
    }

    fun getSchedule(): NextScheduleUiModel? {
        return _schedule
    }

    override fun onTimeOut(time: LocalDateTime) {
        _listener?.onTimeOut()
    }

    private fun render() {
        if (_schedule == null) {
            binding.timeLeft.setTime(null)
            binding.layoutSchedule.visibility = GONE
            binding.layoutNoSchedule.visibility = VISIBLE
            return
        }

        val schedule = _schedule!!

        binding.apply {
            timeLeft.setTime(schedule.items.first().time)
            textViewLabel.text = _labelText ?: context.getText(R.string.next_schedule_label)
            textViewNoSchedule.text = _noScheduleText ?: context.getText(R.string.next_schedule_no_schedule)
            textViewName.text = if (schedule.items.size == 1) {
                schedule.items.first().name
            } else {
                _multipleItemsText ?: context.getText(R.string.next_schedule_multiple_items)
            }
        }

        binding.layoutNoSchedule.visibility = GONE
        binding.layoutSchedule.visibility = VISIBLE
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        binding.recyclerView.adapter = null
        _listener = null
        _adapter?.submitList(null)
    }

    interface NextScheduleListener {
        fun onTimeOut()
        fun onNextScheduleClick(item: NextScheduleUiModel)
    }
}
