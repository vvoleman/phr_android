package cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.binder

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleCoroutineScope
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.problemCategory.ColorAdapter
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.common.ui.model.healthcare.core.SpecificMedicalWorkerUiModel
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.DiagnoseUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.mapper.ProblemCategoryUiModelToColorMapper
import cz.vvoleman.phr.featureMedicalRecord.ui.model.ImageItemUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter.ImageAdapter
import java.time.LocalDate

class AddEditBinder(
    private val problemCategoryMapper: ProblemCategoryUiModelToColorMapper,
    private val diagnoseMapper: DiagnoseUiModelToPresentationMapper,
    private val specificWorkerMapper: SpecificMedicalWorkerUiModelToPresentationMapper,
) :
    BaseViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding, AddEditBinder.Notification>(),
    ImageAdapter.OnAdapterItemListener {

    private lateinit var adapter: ImageAdapter

    override fun firstBind(
        viewBinding: FragmentAddEditMedicalRecordBinding,
        viewState: AddEditViewState
    ) {
        viewBinding.datePicker.setDate(viewState.visitDate ?: LocalDate.now())

        viewBinding.diagnoseSelector.setSelected(viewState.diagnose?.let { diagnoseMapper.toUi(it) })

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

        viewBinding.spinnerMedicalWorker.apply {
            val items = viewState.allMedicalWorkers.map { specificWorkerMapper.toUi(it) }
            setAdapter(
                ArrayAdapter(
                    fragmentContext,
                    android.R.layout.simple_spinner_item,
                    items
                )
            )
            setOnItemClickListener { _, _, position, _ ->
                val item = adapter.getItem(position) as SpecificMedicalWorkerUiModel
                notify(Notification.MedicalWorkerSelected(item))
            }

            if (viewState.specificMedicalWorkerId != null) {
                val item = items.first { it.id == viewState.specificMedicalWorkerId }
                setText(item.toString(), false)
            }
        }
    }

    override fun bind(viewBinding: FragmentAddEditMedicalRecordBinding, viewState: AddEditViewState) {
        super.bind(viewBinding, viewState)

        adapter.submitList(viewState.assets.map { ImageItemUiModel(it) })
        viewBinding.textViewCurrentSizeFiles.text = viewState.assets.size.toString()
        viewBinding.buttonAddFile.isEnabled = viewState.canAddMoreFiles()
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
        data class MedicalWorkerSelected(val item: SpecificMedicalWorkerUiModel) : Notification()
    }

    override fun onItemClicked(item: ImageItemUiModel) {
        notify(Notification.FileClick(item))
    }

    override fun onItemDeleted(item: ImageItemUiModel) {
        notify(Notification.FileDelete(item))
    }

}
