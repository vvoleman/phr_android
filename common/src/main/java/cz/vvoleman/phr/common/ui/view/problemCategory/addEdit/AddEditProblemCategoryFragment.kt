package cz.vvoleman.phr.common.ui.view.problemCategory.addEdit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit.AddEditProblemCategoryNotification
import cz.vvoleman.phr.common.presentation.model.problemCategory.addEdit.AddEditProblemCategoryViewState
import cz.vvoleman.phr.common.presentation.viewmodel.problemCategory.AddEditProblemCategoryViewModel
import cz.vvoleman.phr.common.ui.mapper.problemCategory.destination.AddEditProblemCategoryDestinationUiMapper
import cz.vvoleman.phr.common.utils.textChanges
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditProblemCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AddEditProblemCategoryFragment :
    BaseFragment<AddEditProblemCategoryViewState, AddEditProblemCategoryNotification, FragmentAddEditProblemCategoryBinding>() {

    override val viewModel: AddEditProblemCategoryViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditProblemCategoryDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditProblemCategoryViewState, FragmentAddEditProblemCategoryBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddEditProblemCategoryBinding {
        return FragmentAddEditProblemCategoryBinding.inflate(inflater, container, false)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun setupListeners() {
        super.setupListeners()

        binding.buttonSave.setOnClickListener {
            viewModel.onSave()
        }

        binding.editTextProblemCategoryName.textChanges()
            .debounce(300)
            .onEach {
                viewModel.onNameChanged(it?.toString())
                if (it != null) {
                    binding.textInputLayoutProblemCategoryName.error = null
                }
            }
            .launchIn(lifecycleScope)

        val binder = viewStateBinder as AddEditProblemCategoryBinder
        collectLatestLifecycleFlow(binder.notification) {
            when (it) {
                is AddEditProblemCategoryBinder.Notification.ColorSelected -> {
                    viewModel.onColorSelected(it.value)
                    binding.textInputLayoutColors.error = null
                }
            }
        }
    }

    override fun handleNotification(notification: AddEditProblemCategoryNotification) {
        when (notification) {
            is AddEditProblemCategoryNotification.MissingFields -> {
                showErrorFields(notification.fields)
            }
        }
    }

    private fun showErrorFields(missingFields: List<AddEditProblemCategoryViewState.RequiredField>) {
        showSnackbar(R.string.error_missing_fields, Snackbar.LENGTH_SHORT)

        if (missingFields.contains(AddEditProblemCategoryViewState.RequiredField.NAME)) {
            binding.textInputLayoutProblemCategoryName.error = getString(R.string.error_required)
        }

        if (missingFields.contains(AddEditProblemCategoryViewState.RequiredField.COLOR)) {
            binding.textInputLayoutColors.error = getString(R.string.error_required)
        }
    }
}
