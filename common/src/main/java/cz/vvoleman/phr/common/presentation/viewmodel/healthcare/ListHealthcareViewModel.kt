package cz.vvoleman.phr.common.presentation.viewmodel.healthcare

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListHealthcareViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListHealthcareViewState, ListHealthcareNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListHealthcareViewModel"

    override fun initState(): ListHealthcareViewState {
        return ListHealthcareViewState()
    }

    override fun onInit() {
        super.onInit()

        Log.d(TAG, "launching onInit")
    }
}
