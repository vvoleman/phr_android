package cz.vvoleman.featureMeasurement.ui.view.addEdit

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import cz.vvoleman.featureMeasurement.databinding.FragmentAddEditMeasurementBinding
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementNotification
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder

class AddEditMeasurementBinder :
    BaseViewStateBinder<AddEditMeasurementViewState, FragmentAddEditMeasurementBinding, AddEditMeasurementNotification>() {

    override fun init(
        viewBinding: FragmentAddEditMeasurementBinding,
        context: Context,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)
    }
}
