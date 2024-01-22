package cz.vvoleman.phr.featureMeasurement.ui.view.addEdit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import cz.vvoleman.featureMeasurement.databinding.FragmentAddEditMeasurementBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit.AddEditMeasurementNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupFieldUiToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.UnitGroupUiModelToPresentationMapper
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.mapper.frequencySelector.FrequencyDayUiModelToPresentationMapper

class AddEditMeasurementBinder(
    private val fieldMapper: MeasurementGroupFieldUiToPresentationMapper,
    private val unitGroupMapper: UnitGroupUiModelToPresentationMapper,
    private val frequencyMapper: FrequencyDayUiModelToPresentationMapper
) :
    BaseViewStateBinder<AddEditMeasurementViewState, FragmentAddEditMeasurementBinding, AddEditMeasurementNotification>() {

    override fun init(
        viewBinding: FragmentAddEditMeasurementBinding,
        context: Context,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)
    }

    override fun firstBind(viewBinding: FragmentAddEditMeasurementBinding, viewState: AddEditMeasurementViewState) {
        super.firstBind(viewBinding, viewState)

        viewBinding.fieldEditor.setUnitGroups(viewState.unitGroups.map { unitGroupMapper.toUi(it) })
        viewBinding.editTextName.setText(viewState.name)
    }

    override fun bind(viewBinding: FragmentAddEditMeasurementBinding, viewState: AddEditMeasurementViewState) {
        super.bind(viewBinding, viewState)

        Log.d("AddEditMeasurementBinder", "bind: $viewState")
        viewBinding.fieldEditor.setItems(fieldMapper.toUi(viewState.fields))
        viewBinding.timeSelector.setTimes(viewState.times)
        viewBinding.frequencySelector.setDays(viewState.frequencyDays.map { frequencyMapper.toUi(it) })

        if (!viewBinding.frequencySelector.hasEverydaySet()) {
            Log.d("AddEditMeasurementBinder", "!hasEverydaySet: ${viewState.frequencyDaysDefault}")
            viewBinding.frequencySelector.setDaysEveryday(
                viewState.frequencyDaysDefault.map {
                    frequencyMapper.toUi(it)
                }
            )
        }
    }
}
