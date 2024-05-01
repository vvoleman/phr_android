package cz.vvoleman.phr.common.presentation.viewmodel.patient

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.model.FieldErrorState
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.patient.AddEditPatientDomainModel
import cz.vvoleman.phr.common.domain.usecase.patient.addedit.GetPatientByIdUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.addedit.SavePatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.addedit.AddEditPatientDestination
import cz.vvoleman.phr.common.presentation.model.patient.addedit.AddEditPatientNotification
import cz.vvoleman.phr.common.presentation.model.patient.addedit.AddEditViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEditPatientViewModel @Inject constructor(
    private val getPatientByIdUseCase: GetPatientByIdUseCase,
    private val savePatientUseCase: SavePatientUseCase,
    private val patientPresentationModelToDomainMapper: PatientPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditViewState, AddEditPatientNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "AddEditPatientViewModel"

    override suspend fun initState(): AddEditViewState {
        val patient = getPatient()
        return AddEditViewState(
            patient = patient
        )
    }

    fun onSave(name: String?, birthDate: LocalDate? = null) = viewModelScope.launch {
        if (!validateInput(name, birthDate)) return@launch

        val model = AddEditPatientDomainModel(id = currentViewState.patient?.id, name = name!!, birthDate = birthDate)
        savePatientUseCase.execute(model) {
            val presentationModel = patientPresentationModelToDomainMapper.toPresentation(it)
            notify(AddEditPatientNotification.PatientSaved(presentationModel))
            navigateTo(AddEditPatientDestination.PatientSaved(presentationModel.id))
        }
    }

    private fun validateInput(name: String?, birthDate: LocalDate? = null): Boolean {
        val errors = mutableMapOf<String, List<FieldErrorState>>()
        if (name.isNullOrEmpty()) {
            errors["name"] = listOf(FieldErrorState.EMPTY)
        }

        // Check if birthDate is in the past
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            errors["birthDate"] = listOf(FieldErrorState.INVALID)
        }

        updateViewState(currentViewState.copy(errorFields = errors))
        Log.d(TAG, "validateInput: $errors, ${errors.isEmpty()}")

        return errors.isEmpty()
    }

    private suspend fun getPatient(): PatientPresentationModel?{
        val id = savedStateHandle.get<String>(PATIENT_KEY) ?: return null

        val patient = getPatientByIdUseCase.executeInBackground(id)

        if (patient == null) {
            notify(AddEditPatientNotification.Error)
            navigateTo(AddEditPatientDestination.Back)
            return null
        }

        return patientPresentationModelToDomainMapper.toPresentation(patient)

    }

    companion object {
        const val PATIENT_KEY = "patientId"
    }
}
