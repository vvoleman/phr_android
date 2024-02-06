package cz.vvoleman.phr.featureEvent.ui.view.addEdit

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureEvent.databinding.FragmentAddEditEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventViewState

class AddEditEventBinder :
    BaseViewStateBinder<AddEditEventViewState, FragmentAddEditEventBinding, AddEditEventBinder.Notification>() {
    sealed class Notification
}
