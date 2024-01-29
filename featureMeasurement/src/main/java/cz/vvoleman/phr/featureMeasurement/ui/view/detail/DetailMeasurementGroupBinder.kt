package cz.vvoleman.phr.featureMeasurement.ui.view.detail

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentDetailMeasurementGroupBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupViewState

class DetailMeasurementGroupBinder :
    BaseViewStateBinder<DetailMeasurementGroupViewState, FragmentDetailMeasurementGroupBinding, DetailMeasurementGroupBinder.Notification>() {

    sealed class Notification {}

}
