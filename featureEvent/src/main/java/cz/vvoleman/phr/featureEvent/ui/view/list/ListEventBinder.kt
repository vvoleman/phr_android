package cz.vvoleman.phr.featureEvent.ui.view.list

import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.featureEvent.databinding.FragmentListEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventViewState
import cz.vvoleman.phr.featureEvent.ui.factory.ListEventFactory
import cz.vvoleman.phr.featureEvent.ui.mapper.core.EventUiModelToPresentationMapper

class ListEventBinder(
    private val eventFactory: ListEventFactory,
    private val eventMapper: EventUiModelToPresentationMapper
) :
    BaseViewStateBinder<ListEventViewState, FragmentListEventBinding, ListEventBinder.Notification>() {

    override fun firstBind(viewBinding: FragmentListEventBinding, viewState: ListEventViewState) {
        super.firstBind(viewBinding, viewState)

        val events = viewState.events.map { (date, eventList) ->
            date to eventList.map { eventMapper.toUi(it) }
        }.toMap()
        val containers = eventFactory.create(events)
        val groupieAdapter = GroupieAdapter().apply {
            addAll(containers)
        }

        viewBinding.recyclerView.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(true)
        }
    }

    sealed class Notification

}
