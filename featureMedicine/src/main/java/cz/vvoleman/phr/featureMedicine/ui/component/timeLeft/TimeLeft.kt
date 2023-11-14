package cz.vvoleman.phr.featureMedicine.ui.component.timeLeft

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.vvoleman.phr.common.utils.getHoursPart
import cz.vvoleman.phr.common.utils.getMinutesPart
import cz.vvoleman.phr.common.utils.getSecondsPart
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ViewTimeLeftBinding
import java.time.Duration
import java.time.LocalDateTime

class TimeLeft @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewTimeLeftBinding
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    private var _listener: TimeLeftListener? = null

    private var time: LocalDateTime? = null

    init {
        binding = ViewTimeLeftBinding.inflate(LayoutInflater.from(context), this, true)

        binding.tvTimeLeft.text = "-"
    }

    fun setTime(time: LocalDateTime?) {
        this.time = time
        cancelRunnable()
        startRunnable()
    }

    fun getCurrentTime(): LocalDateTime? {
        return time
    }

    fun setListener(listener: TimeLeftListener) {
        _listener = listener
    }

    private fun render(duration: Duration) {
        val resourceId: Int?
        val paramA: Int?
        val paramB: Int?

        if (duration.toHours() < 1) {
            resourceId = R.string.time_left_minutes
            paramA = duration.toMinutes().toInt()
            paramB = duration.getSecondsPart()
        } else if (duration.toDays() < 1) {
            resourceId = R.string.time_left_hours
            paramA = duration.toHours().toInt()
            paramB = duration.getMinutesPart()
        } else {
            resourceId = R.string.time_left_days
            paramA = duration.toDays().toInt()
            paramB = duration.getHoursPart()
        }

        val text = String.format(resources.getString(resourceId), paramA, paramB)
        binding.tvTimeLeft.text = text
    }

    private fun startRunnable() {
        runnable = object : Runnable {
            override fun run() {
                if (time == null) {
                    return
                }

                if (time!!.isAfter(LocalDateTime.now())) {
                    val diff = Duration.between(LocalDateTime.now(), time)
                    render(diff)

                    handler.postDelayed(this, 1000)
                } else {
                    _listener?.onTimeOut(time!!)
                    Log.d(TAG, "Runnable has been stopped")
                }
            }
        }

        handler.post(runnable!!)
    }

    private fun cancelRunnable() {
        runnable?.let {
            handler.removeCallbacks(it)
            runnable = null
        }
    }

    companion object {
        const val TAG = "TimeLeft"
    }

    interface TimeLeftListener {
        fun onTimeOut(time: LocalDateTime)
    }

}