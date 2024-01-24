package cz.vvoleman.phr.featureMeasurement.ui.view.list

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.component.nextSchedule.NextScheduleUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.list.ListMeasurementViewState

class ListMeasurementBinder(
    private val nextScheduleMapper: NextScheduleUiModelToPresentationMapper
) :
    BaseViewStateBinder<ListMeasurementViewState, FragmentListMeasurementBinding, ListMeasurementNotification>() {

    override fun init(
        viewBinding: FragmentListMeasurementBinding,
        context: Context,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)
    }

    override fun bind(viewBinding: FragmentListMeasurementBinding, viewState: ListMeasurementViewState) {
        super.bind(viewBinding, viewState)

        if (viewState.selectedNextSchedule?.dateTime != viewBinding.nextSchedule.getSchedule()?.dateTime) {
            val nextScheduleUi = viewState.selectedNextSchedule?.let { nextScheduleMapper.toUi(it) }
            viewBinding.nextSchedule.setSchedule(nextScheduleUi)
        }
    }
}
