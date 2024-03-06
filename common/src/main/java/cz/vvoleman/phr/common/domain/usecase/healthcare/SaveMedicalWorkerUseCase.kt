package cz.vvoleman.phr.common.domain.usecase.healthcare

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.healthcare.save.SaveMedicalWorkerRequest
import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceWithInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.RemoveSpecificMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalFacilityRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveMedicalWorkerRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.SaveSpecificMedicalWorkerRepository

class SaveMedicalWorkerUseCase(
    private val saveMedicalFacilityRepository: SaveMedicalFacilityRepository,
    private val saveMedicalWorkerRepository: SaveMedicalWorkerRepository,
    private val getSpecificMedicalWorkersRepository: GetSpecificMedicalWorkersRepository,
    private val saveSpecificMedicalWorkerRepository: SaveSpecificMedicalWorkerRepository,
    private val removeSpecificMedicalWorkerRepository: RemoveSpecificMedicalWorkerRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SaveMedicalWorkerRequest, String>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SaveMedicalWorkerRequest): String {
        val facilities = request.medicalServices.map { it.facility }.distinctBy { it.id }
        val services = mutableMapOf<String, MedicalServiceDomainModel>()
        facilities.map { it.services }.flatten().onEach {
            services[it.medicalService.id] = it.medicalService
        }

        val servicesInfo = request.medicalServices.map {
            MedicalServiceWithInfoDomainModel(services[it.serviceId]!!, it.email, it.telephone)
        }

        saveMedicalFacilityRepository.saveMedicalFacility(facilities)

        val medicalWorker = MedicalWorkerDomainModel(
            id = request.id,
            name = request.name,
            patientId = request.patientId
        )

        val medicalWorkerId = saveMedicalWorkerRepository.saveMedicalWorker(medicalWorker)

        val specificWorkers = getSpecificMedicalWorkersRepository.getSpecificMedicalWorkers(medicalWorkerId)
        val specificWorkersGrouped = specificWorkers.groupBy { it.medicalService.id }
        val previousServiceIds = specificWorkers.map { it.medicalService.id }.toMutableList()

//        val removeServiceIds = mutableListOf<String>()
        val updatedWorkers = mutableListOf<SpecificMedicalWorkerDomainModel>()

        servicesInfo.onEach { info ->
            if (specificWorkers.isNotEmpty() && previousServiceIds.contains(info.medicalService.id)) {
                previousServiceIds.remove(info.medicalService.id)
            }

            updatedWorkers.add(
                SpecificMedicalWorkerDomainModel(
                    id = specificWorkersGrouped[info.medicalService.id]?.first()?.id,
                    medicalWorker = medicalWorker.copy(id = medicalWorkerId),
                    medicalService = info.medicalService,
                    telephone = info.telephone,
                    email = info.email
                )
            )
        }

        removeSpecificMedicalWorkerRepository.removeSpecificMedicalWorker(medicalWorkerId, previousServiceIds)
        saveSpecificMedicalWorkerRepository.saveSpecificMedicalWorker(updatedWorkers)

        return medicalWorkerId
    }
}
