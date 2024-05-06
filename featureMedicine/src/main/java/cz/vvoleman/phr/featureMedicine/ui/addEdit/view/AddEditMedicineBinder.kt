package cz.vvoleman.phr.featureMedicine.ui.addEdit.view

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.problemCategory.ColorAdapter
import cz.vvoleman.phr.common.ui.mapper.frequencySelector.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ProblemCategoryUiModelToColorMapper
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineUiModelToPresentationMapper
import java.time.LocalTime

@Suppress("MaximumLineLength", "MaxLineLength")
class AddEditMedicineBinder(
    private val medicineMapper: MedicineUiModelToPresentationMapper,
    private val timeMapper: TimeUiModelToPresentationMapper,
    private val frequencyMapper: FrequencyDayUiModelToPresentationMapper,
    private val problemCategoryMapper: ProblemCategoryUiModelToColorMapper,
) : BaseViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding, AddEditMedicineBinder.Notification>() {

    private val existingTimes = mutableListOf<LocalTime>()

    override fun firstBind(viewBinding: FragmentAddEditMedicineBinding, viewState: AddEditMedicineViewState) {
        super.firstBind(viewBinding, viewState)

        val times = viewState.times.map { timeMapper.toUi(it) }
        existingTimes.addAll(times.map { it.time })
        viewBinding.timeSelector.setTimes(times)
        viewBinding.frequencySelector.setDays(viewState.frequencyDays.map { frequencyMapper.toUi(it) })

        val categories = problemCategoryMapper.toColor(viewState.availableProblemCategories)
        val categoryAdapter = ColorAdapter(viewBinding.root.context, categories)

        viewBinding.spinnerProblemCategory.apply {
            setAdapter(categoryAdapter)
            setOnItemClickListener { _, _, position, _ ->
                val color = categoryAdapter.getItem(position)
                notify(Notification.ProblemCategorySelected(color?.name))
            }
        }
        viewState.availableProblemCategories.firstOrNull { it.id == viewState.problemCategory?.id }
            ?.let {
                viewBinding.spinnerProblemCategory.setText(it.name, false)
            }
    }

    override fun bind(viewBinding: FragmentAddEditMedicineBinding, viewState: AddEditMedicineViewState) {
        super.bind(viewBinding, viewState)

        val newTimes = viewState.times.map { it.time }

        if (existingTimes != newTimes) {
            existingTimes.clear()
            existingTimes.addAll(newTimes)
            viewBinding.timeSelector.setTimes(viewState.times.map { timeMapper.toUi(it) })
        }
        viewBinding.medicineSelector.setSelectedMedicine(viewState.selectedMedicine?.let { medicineMapper.toUi(it) })

        if (!viewBinding.frequencySelector.hasEverydaySet()) {
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
