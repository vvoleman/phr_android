package cz.vvoleman.phr.common.domain.usecase.patient

import android.util.Log
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.repository.patient.DeletePatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetAllPatientsRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetSelectedPatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.SwitchSelectedPatientRepository
import kotlinx.coroutines.flow.first

class DeletePatientUseCase(
    private val deletePatientRepository: DeletePatientRepository,
    private val getSelectedPatientRepository: GetSelectedPatientRepository,
    private val getAllPatientsRepository: GetAllPatientsRepository,
    private val switchSelectedPatientRepository: SwitchSelectedPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, Boolean>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): Boolean {
        val selectedPatient = getSelectedPatientRepository.getSelectedPatient().first()

        if (selectedPatient.id == request) {
            val allPatients = getAllPatientsRepository.getAll()

            if (allPatients.size <= 1) {
                return false
            }

            val newSelectedPatient = allPatients.first { it.id != request }

            switchSelectedPatientRepository.switchSelectedPatient(newSelectedPatient.id)
            Log.i("DeletePatientUseCase", "Switched selected patient to ${newSelectedPatient.id}")
        }

        return deletePatientRepository.deletePatient(request)
    }
}
