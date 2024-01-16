package cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.binder

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleCoroutineScope
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.problemCategory.ColorAdapter
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.ProblemCategoryUiModelToColorMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.model.ImageItemUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter.ImageAdapter
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddEditBinder(
    private val problemCategoryMapper: ProblemCategoryUiModelToColorMapper
) :
    BaseViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding, AddEditBinder.Notification>(),
    ImageAdapter.OnAdapterItemListener {

    private lateinit var adapter: ImageAdapter

    override fun firstBind(
        viewBinding: FragmentAddEditMedicalRecordBinding,
        viewState: AddEditViewState
    ) {
        viewBinding.datePicker.setDate(viewState.visitDate ?: LocalDate.now())
        viewBinding.textViewCurrentSizeFiles.text = viewState.assets.size.toString()
        viewBinding.buttonAddFile.isEnabled = viewState.canAddMoreFiles()

        lifecycleScope.launch {
//            viewBinding..setData(
//                viewState.diagnoseSpinnerList.map { DiagnoseItemUiModel(it.id, it.name, it.parent) }
//            )
        }

        val categoryAdapter =
            ColorAdapter(viewBinding.root.context, problemCategoryMapper.toColor(viewState.allProblemCategories))
        viewBinding.spinnerProblemCategory.apply {
            setAdapter(categoryAdapter)
            setOnItemClickListener { _, _, position, _ ->
                val color = categoryAdapter.getItem(position)
                notify(Notification.ProblemCategorySelected(color?.name))
            }
        }
        viewBinding.spinnerMedicalWorker.apply {
            adapter = ArrayAdapter(
                fragmentContext,
                android.R.layout.simple_spinner_item,
                viewState.allMedicalWorkers.map {
                    it.name
                }
            )
        }
    }

    override fun bind(viewBinding: FragmentAddEditMedicalRecordBinding, viewState: AddEditViewState) {
        super.bind(viewBinding, viewState)

        adapter.submitList(viewState.assets.map { ImageItemUiModel(it) })
    }

    override fun init(
        viewBinding: FragmentAddEditMedicalRecordBinding,
        context: Context,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)

        viewBinding.textViewTotalSizeFiles.text = AddEditViewState.MAX_FILES.toString()
        adapter = viewBinding.recyclerViewFiles.adapter as ImageAdapter
    }

    sealed class Notification {
        object AddFile : Notification()
        data class FileClick(val item: ImageItemUiModel) : Notification()
        data class FileDelete(val item: ImageItemUiModel) : Notification()
        data class ProblemCategorySelected(val value: String?) : Notification()
    }

    override fun onItemClicked(item: ImageItemUiModel) {
        notify(Notification.FileClick(item))
    }

    override fun onItemDeleted(item: ImageItemUiModel) {
        notify(Notification.FileDelete(item))
    }

}
