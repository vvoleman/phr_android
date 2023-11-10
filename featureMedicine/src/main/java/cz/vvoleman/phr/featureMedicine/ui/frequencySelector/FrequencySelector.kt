package cz.vvoleman.phr.featureMedicine.ui.frequencySelector

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.vvoleman.phr.featureMedicine.databinding.ViewFrequencySelectorBinding
import java.time.DayOfWeek

class FrequencySelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), FrequencyDayAdapter.FrequencyDayListener {

    private val binding: ViewFrequencySelectorBinding
    private var _days: MutableMap<DayOfWeek, FrequencyDayUiModel> = mutableMapOf()
    private var _daysEveryday: List<FrequencyDayUiModel> = listOf()
    private var _currentState: SelectorState = SelectorState.EVERY_DAY
    private var _adapter: FrequencyDayAdapter? = null

    private var _listener: FrequencySelectorListener? = null

    init {
        binding = ViewFrequencySelectorBinding.inflate(LayoutInflater.from(context), this, true)

        _adapter = FrequencyDayAdapter(this)
        binding.recyclerViewDays.apply {
            adapter = _adapter
            setHasFixedSize(true)
            itemAnimator = null
        }

        setupListeners()
        updateState(_currentState)
    }

    fun setDaysEveryday(days: List<FrequencyDayUiModel>) {
        _daysEveryday = days
    }

    fun hasEverydaySet(): Boolean {
        return _daysEveryday.isNotEmpty()
    }

    private fun setupListeners() {
        binding.apply {
            buttonFrequencyEveryDay.setOnClickListener {
                if (_currentState == SelectorState.CUSTOM) {
                    updateState(SelectorState.EVERY_DAY)
                }
            }

            buttonFrequencyCustom.setOnClickListener {
                if (_currentState == SelectorState.EVERY_DAY) {
                    updateState(SelectorState.CUSTOM)
                }
            }
        }
    }

    private fun updateState(state: SelectorState) {
        Log.d("FrequencySelector", "updateState: $state")
        _currentState = state
        when (state) {
            SelectorState.EVERY_DAY -> {
                binding.recyclerViewDays.isEnabled = false
                binding.recyclerViewDays.visibility = GONE
            }
            SelectorState.CUSTOM -> {
                Log.d("FrequencySelector", "updateState: ${_days.values.toList()}")
                binding.recyclerViewDays.visibility = VISIBLE
            }
        }
    }

    fun setDays(days: List<FrequencyDayUiModel> = listOf()) {
        days.forEach {
            _days[it.day] = it
        }

        val values = _days.values.toList()
        val state = if (values.filter { it.isSelected }.size == 7 || values.isEmpty()) {
            SelectorState.EVERY_DAY
        } else {
            SelectorState.CUSTOM
        }
        _adapter!!.submitList(values)
        if (state != _currentState) {
            updateState(state)
        }

        _listener?.onValueChange(values)

    }

    fun setListener(listener: FrequencySelectorListener) {
        _listener = listener
    }

    interface FrequencySelectorListener {
        fun onValueChange(days: List<FrequencyDayUiModel>)
    }

    enum class SelectorState {
        EVERY_DAY, CUSTOM
    }

    override fun onValueChange(item: FrequencyDayUiModel) {
        setDays(listOf(item.copy(isSelected = !item.isSelected)))
    }

}