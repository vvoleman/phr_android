package cz.vvoleman.phr.common.domain.usecase.patient.addedit

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.patient.AddEditPatientDomainModel
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.request.SaveProblemCategoryRequest
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByIdRepository
import cz.vvoleman.phr.common.domain.repository.patient.SavePatientRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.SaveProblemCategoryRepository
import java.time.LocalDateTime

class SavePatientUseCase(
    private val saveProblemCategoryRepository: SaveProblemCategoryRepository,
    private val savePatientRepository: SavePatientRepository,
    private val getPatientByIdRepository: GetPatientByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<AddEditPatientDomainModel, PatientDomainModel>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: AddEditPatientDomainModel): PatientDomainModel {
        val id = savePatientRepository.savePatient(request)

        if (request.id == null) {
            val problemCategoryRequest = SaveProblemCategoryRequest(
                name = "Výchozí",
                createdAt = LocalDateTime.now(),
                color = "#3A6B83",
                patientId = id,
                isDefault = true,
            )
            saveProblemCategoryRepository.saveProblemCategory(problemCategoryRequest)
        }

        return getPatientByIdRepository.getById(id)!!
    }
}
