package cz.vvoleman.phr.featureMedicine.ui.component.scheduleDetailDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.DialogScheduleDetailBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.ScheduleItemWithDetailsUiModel
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

class ScheduleDetailDialogFragment : DialogFragment() {
    private var _binding: DialogScheduleDetailBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogScheduleDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments == null) {
            dismiss()
            return
        }

        val bundle = fromBundle(requireArguments())
        val listener = targetFragment as? ScheduleDetailAdapter.ScheduleDetailListener

        if (listener == null) {
            dismiss()
            return
        }

        val localDayOfWeek = bundle.localDateTime.dayOfWeek.getDisplayName(
            TextStyle.FULL,
            Locale.getDefault()
        )
        val localTime = bundle.localDateTime.toLocalTime().toString()
        binding.textViewTime.text =
            view.resources.getString(R.string.dialog_schedule_detail_time, localDayOfWeek, localTime)

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        val scheduleAdapter = ScheduleDetailAdapter(listener)
        scheduleAdapter.submitList(bundle.scheduleItems)
        binding.recyclerView.apply {
            adapter = scheduleAdapter
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    companion object {
        const val TAG = "ScheduleDetailDialogFragment"
        const val ARG_LOCAL_DATE_TIME = "localDateTime"
        const val ARG_SCHEDULE_ITEMS = "scheduleItems"

        fun newInstance(
            localDateTime: LocalDateTime,
            scheduleItems: List<ScheduleItemWithDetailsUiModel>
        ): ScheduleDetailDialogFragment {
            val fragment = ScheduleDetailDialogFragment()
            val bundle = getBundle(localDateTime, scheduleItems)
            fragment.arguments = bundle
            return fragment
        }

        fun getBundle(localDateTime: LocalDateTime, scheduleItems: List<ScheduleItemWithDetailsUiModel>): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(ARG_LOCAL_DATE_TIME, localDateTime)
            bundle.putParcelableArrayList(ARG_SCHEDULE_ITEMS, ArrayList(scheduleItems))
            return bundle
        }

        fun fromBundle(bundle: Bundle): FragmentArguments {
            val localDateTime = bundle.getSerializable(ARG_LOCAL_DATE_TIME) as LocalDateTime
            val scheduleItems = bundle.getParcelableArrayList<ScheduleItemWithDetailsUiModel>(ARG_SCHEDULE_ITEMS)!!
            return FragmentArguments(localDateTime, scheduleItems)
        }

        data class FragmentArguments(
            val localDateTime: LocalDateTime,
            val scheduleItems: List<ScheduleItemWithDetailsUiModel>
        )
    }
}
