package cz.vvoleman.phr.featureMeasurement.ui.view.addEdit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.problemCategory.ColorAdapter
import cz.vvoleman.phr.common.ui.mapper.frequencySelector.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToColorMapper
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentAddEditMeasurementBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupFieldUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.UnitGroupUiModelToPresentationMapper

class AddEditMeasurementBinder(
    private val fieldMapper: MeasurementGroupFieldUiModelToPresentationMapper,
    private val unitGroupMapper: UnitGroupUiModelToPresentationMapper,
    private val frequencyMapper: FrequencyDayUiModelToPresentationMapper,
    private val problemCategoryMapper: ProblemCategoryUiModelToColorMapper,
) :
    BaseViewStateBinder<AddEditMeasurementViewState, FragmentAddEditMeasurementBinding, AddEditMeasurementBinder.Notification>() {

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
        viewBinding.timeSelector.setTimes(viewState.times)
        viewBinding.editTextName.setText(viewState.name)
        val categories = problemCategoryMapper.toColor(viewState.allProblemCategories)
        val categoryAdapter = ColorAdapter(viewBinding.root.context, categories)
        viewBinding.spinnerProblemCategory.apply {
            setAdapter(categoryAdapter)
            setOnItemClickListener { _, _, position, _ ->
                val color = categoryAdapter.getItem(position)
                notify(Notification.ProblemCategorySelected(color?.name))
            }
        }
        viewState.allProblemCategories.firstOrNull { it.id == viewState.problemCategoryId }
            ?.let {
                viewBinding.spinnerProblemCategory.setText(it.name, false)
            }
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

    sealed class Notification {
        data class ProblemCategorySelected(val value: String?) : Notification()
    }
}
