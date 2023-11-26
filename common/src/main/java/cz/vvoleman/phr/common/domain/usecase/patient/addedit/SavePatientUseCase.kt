package cz.vvoleman.phr.common.domain.usecase.patient.addedit

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.AddEditPatientDomainModel
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByIdRepository
import cz.vvoleman.phr.common.domain.repository.patient.SavePatientRepository

class SavePatientUseCase(
    private val savePatientRepository: SavePatientRepository,
    private val getPatientByIdRepository: GetPatientByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<AddEditPatientDomainModel, PatientDomainModel>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: AddEditPatientDomainModel): PatientDomainModel {
        val id = savePatientRepository.savePatient(request)

        return getPatientByIdRepository.getById(id)!!
    }
}
