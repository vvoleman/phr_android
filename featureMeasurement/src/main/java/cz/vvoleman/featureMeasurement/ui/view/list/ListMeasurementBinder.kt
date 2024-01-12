package cz.vvoleman.featureMeasurement.ui.view.list

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import cz.vvoleman.featureMeasurement.databinding.FragmentListMeasurementBinding
import cz.vvoleman.featureMeasurement.presentation.model.list.ListMeasurementNotification
import cz.vvoleman.featureMeasurement.presentation.model.list.ListMeasurementViewState
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder

class ListMeasurementBinder :
    BaseViewStateBinder<ListMeasurementViewState, FragmentListMeasurementBinding, ListMeasurementNotification>() {

    override fun init(
        viewBinding: FragmentListMeasurementBinding,
        context: Context,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)
    }
}
