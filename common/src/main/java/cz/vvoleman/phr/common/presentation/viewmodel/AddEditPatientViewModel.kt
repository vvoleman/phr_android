package cz.vvoleman.phr.common.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.model.FieldErrorState
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.AddEditPatientDomainModel
import cz.vvoleman.phr.common.domain.usecase.addedit.GetPatientByIdUseCase
import cz.vvoleman.phr.common.domain.usecase.addedit.SavePatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.addedit.AddEditPatientDestination
import cz.vvoleman.phr.common.presentation.model.addedit.AddEditPatientNotification
import cz.vvoleman.phr.common.presentation.model.addedit.AddEditViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEditPatientViewModel @Inject constructor(
    private val getPatientByIdUseCase: GetPatientByIdUseCase,
    private val savePatientUseCase: SavePatientUseCase,
    private val patientPresentationModelToDomainMapper: PatientPresentationModelToDomainMapper,
    private val savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditViewState, AddEditPatientNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "AddEditPatientViewModel"

    override fun initState(): AddEditViewState {
        return AddEditViewState()
    }

    override fun onInit() {
        super.onInit()

        val patientId = savedStateHandle.get<String>(PATIENT_KEY)
        if (patientId != null) {
            loadPatient(patientId)
        }
    }

    fun onSave(name: String?, birthDate: LocalDate? = null) = viewModelScope.launch {
        Log.d(TAG, "onSave: $name, $birthDate")
        if (!validateInput(name, birthDate)) return@launch

        val model = AddEditPatientDomainModel(id = currentViewState.patient?.id, name=name!!, birthDate = birthDate)
        savePatientUseCase.execute(model) {
            val presentationModel = patientPresentationModelToDomainMapper.toPresentation(it)
            notify(AddEditPatientNotification.PatientSaved(presentationModel))
            navigateTo(AddEditPatientDestination.PatientSaved(presentationModel.id))
        }
    }

    private fun validateInput(name: String?, birthDate: LocalDate? = null): Boolean {
        val errors = mutableMapOf<String, List<FieldErrorState>>()
        if (name == null || name.isEmpty()) {
            errors["name"] = listOf(FieldErrorState.EMPTY)
        }
        updateViewState(currentViewState.copy(errorFields = errors))
        Log.d(TAG, "validateInput: $errors, ${errors.isEmpty()}")
        return errors.isEmpty()
    }

    private fun loadPatient(id: String) = viewModelScope.launch {
        getPatientByIdUseCase.execute(id) {
            if (it == null) {
                notify(AddEditPatientNotification.Error)
                navigateTo(AddEditPatientDestination.Back)
                return@execute
            }

            val presentationModel = patientPresentationModelToDomainMapper.toPresentation(it)
            updateViewState(currentViewState.copy(patient = presentationModel))
        }
    }

    companion object {
        const val PATIENT_KEY = "patientId"
    }
}