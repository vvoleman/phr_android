package cz.vvoleman.phr.common.ui.view.healthcare.addEdit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState
import cz.vvoleman.phr.common.ui.mapper.healthcare.AddEditMedicalServiceItemUiModelToPresentationMapper
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditMedicalWorkerBinding

class AddEditMedicalWorkerBinder(
    private val addEditMapper: AddEditMedicalServiceItemUiModelToPresentationMapper
) :
    BaseViewStateBinder<AddEditMedicalWorkerViewState, FragmentAddEditMedicalWorkerBinding, AddEditMedicalWorkerBinder.Notification>() {

    override fun init(
        viewBinding: FragmentAddEditMedicalWorkerBinding,
        context: Context,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        super.init(viewBinding, context, lifecycleScope)
        viewBinding.serviceDetail.setLifecycleScope(lifecycleScope)
    }

    override fun bind(viewBinding: FragmentAddEditMedicalWorkerBinding, viewState: AddEditMedicalWorkerViewState) {
        super.bind(viewBinding, viewState)

        Log.d("AddEditMedicalWorkerBinder", "bind: ${viewState.details.first()}")
        viewBinding.serviceDetail.setItem(addEditMapper.toUi(viewState.details.first()))
    }

    override fun firstBind(viewBinding: FragmentAddEditMedicalWorkerBinding, viewState: AddEditMedicalWorkerViewState) {
        super.firstBind(viewBinding, viewState)

        viewBinding.editTextMedicalWorkerName.setText(viewState.name)
    }

    sealed class Notification
}
