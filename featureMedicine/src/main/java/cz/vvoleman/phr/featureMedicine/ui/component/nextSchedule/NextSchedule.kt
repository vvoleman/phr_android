package cz.vvoleman.phr.featureMedicine.ui.component.nextSchedule

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ViewNextScheduleBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.NextScheduleItemUiModel
import cz.vvoleman.phr.featureMedicine.ui.component.timeLeft.TimeLeft
import java.time.LocalDateTime

class NextSchedule @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), TimeLeft.TimeLeftListener {

    private val binding: ViewNextScheduleBinding
    private var _adapter: ScheduleItemAdapter? = null

    private var _listener: NextScheduleListener? = null
    private var _schedule: NextScheduleItemUiModel? = null

    init {
        binding = ViewNextScheduleBinding.inflate(LayoutInflater.from(context), this, true)

        _adapter = ScheduleItemAdapter()
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

    fun setSchedule(schedule: NextScheduleItemUiModel?) {
        _schedule = schedule

        val list = schedule?.scheduleItems?.map {
            NextScheduleUiModel(
                id = it.scheduleItem.id!!,
                time = schedule.dateTime,
                medicineName = it.medicine.name,
                medicineId = it.medicine.id,
                quantity = it.scheduleItem.quantity.toString(),
                unit = it.scheduleItem.unit,
            )
        } ?: mutableListOf()

        _adapter?.submitList(list)
        render()
    }

    fun setListener(listener: NextScheduleListener) {
        _listener = listener
    }

    fun getSchedule(): NextScheduleItemUiModel? {
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
            timeLeft.setTime(schedule.dateTime)
            textViewMedicineName.text = if (schedule.scheduleItems.size == 1) {
                schedule.scheduleItems.first().medicine.name
            } else{
                context.getText(R.string.multiple_medicines)
            }
        }

        binding.layoutNoSchedule.visibility = GONE
        binding.layoutSchedule.visibility = VISIBLE


    }

    interface NextScheduleListener {
        fun onTimeOut()
        fun onNextScheduleClick(item: NextScheduleItemUiModel)
    }

}