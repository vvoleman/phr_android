package cz.vvoleman.phr.featureEvent.ui.view.list

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureEvent.databinding.FragmentListEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.list.ListEventViewState

class ListEventBinder() :
    BaseViewStateBinder<ListEventViewState, FragmentListEventBinding, ListEventBinder.Notification>() {

    sealed class Notification

}
