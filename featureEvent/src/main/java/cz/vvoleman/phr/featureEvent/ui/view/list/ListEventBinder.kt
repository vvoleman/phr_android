package cz.vvoleman.phr.featureEvent.ui.view.list

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupieAdapter
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.featureEvent.R
import cz.vvoleman.phr.featureEvent.databinding.FragmentListEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventViewState
import cz.vvoleman.phr.featureEvent.ui.factory.ListEventFactory
import cz.vvoleman.phr.featureEvent.ui.mapper.core.EventUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.ui.model.core.EventUiModel
import cz.vvoleman.phr.featureEvent.ui.view.list.groupie.DayItem
import cz.vvoleman.phr.featureEvent.ui.view.list.groupie.MonthContainer

class ListEventBinder(
    private val eventFactory: ListEventFactory,
    private val eventMapper: EventUiModelToPresentationMapper
) :
    BaseViewStateBinder<ListEventViewState, FragmentListEventBinding, ListEventBinder.Notification>(),
    DayItem.EventItemListener {


    override fun firstBind(viewBinding: FragmentListEventBinding, viewState: ListEventViewState) {
        super.firstBind(viewBinding, viewState)

        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(true)
        }

        viewBinding.chipShowAll.setOnClickListener {
            val isToggle = viewBinding.chipShowAll.isChecked
            notify(Notification.ToggleShowAll(isToggle))
        }

        viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                // If user scrolls up and then let go, older events will be loaded
                if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    notify(Notification.LoadOlderEvents)
                }
            }
        })
    }

    override fun bind(viewBinding: FragmentListEventBinding, viewState: ListEventViewState) {
        super.bind(viewBinding, viewState)

        val groupieAdapter = GroupieAdapter().apply {
            addAll(getItems(viewState))
        }

        viewBinding.chipShowAll.isChecked = viewState.currentCount == viewState.totalCount
        viewBinding.recyclerView.adapter = groupieAdapter
        viewBinding.textViewEventCountHidden.text = viewBinding.root.context.getString(
            R.string.event_count_hidden,
            (viewState.totalCount - viewState.currentCount).toString(),
        )
        viewBinding.textViewNoEvents.visibility = if (viewState.currentCount == 0) View.VISIBLE else View.GONE

    }

    private fun getItems(viewState: ListEventViewState): List<MonthContainer> {
        Log.d("ListEventBinder", "getItems: $viewState")
        val events = viewState.events.map { (date, eventList) ->
            date to eventList.map { eventMapper.toUi(it) }
        }.toMap()

        return eventFactory.create(events, this)
    }

    sealed class Notification {
        data class OnOptionsMenuPopup(val item: EventUiModel, val view: View) : Notification()
        object LoadOlderEvents : Notification()
        data class ToggleShowAll(val isToggle: Boolean) : Notification()
    }

    override fun onOptionsMenuPopup(item: EventUiModel, view: View) {
        notify(Notification.OnOptionsMenuPopup(item, view))
    }

}
