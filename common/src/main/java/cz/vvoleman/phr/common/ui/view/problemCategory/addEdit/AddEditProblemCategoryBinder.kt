package cz.vvoleman.phr.common.ui.view.problemCategory.addEdit

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit.AddEditProblemCategoryViewState
import cz.vvoleman.phr.common.ui.adapter.problemCategory.ColorAdapter
import cz.vvoleman.phr.common.ui.mapper.problemCategory.ColorUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.model.problemCategory.ColorUiModel
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditProblemCategoryBinding

class AddEditProblemCategoryBinder(
    private val colorMapper: ColorUiModelToPresentationMapper
) :
    BaseViewStateBinder<
        AddEditProblemCategoryViewState,
        FragmentAddEditProblemCategoryBinding,
        AddEditProblemCategoryBinder.Notification
        >() {

    override fun firstBind(
        viewBinding: FragmentAddEditProblemCategoryBinding,
        viewState: AddEditProblemCategoryViewState
    ) {
        super.firstBind(viewBinding, viewState)

        val colors = viewState.colorList?.map { colorMapper.toUi(it) } ?: emptyList()
        val adapter = ColorAdapter(viewBinding.root.context, colors)

        viewBinding.autoCompleteTextViewColor.apply {
            setAdapter(adapter)
            setOnItemClickListener { _, _, position, _ ->
                val color = adapter.getItem(position)
                setText(color?.name, false)
                notify(Notification.ColorSelected(color?.color))
            }
        }

        viewBinding.editTextProblemCategoryName.setText(viewState.name)

        if (viewState.selectedColor != null) {
            viewBinding.autoCompleteTextViewColor.setText(getColorName(viewState.selectedColor, colors), false)
        }
    }

    sealed class Notification {
        data class ColorSelected(val value: String?) : Notification()
    }

    private fun getColorName(value: String, colorList: List<ColorUiModel>): String {
        return colorList.find { it.color == value }?.name ?: ""
    }
}
