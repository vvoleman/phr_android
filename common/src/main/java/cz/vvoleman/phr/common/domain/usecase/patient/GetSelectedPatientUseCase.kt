package cz.vvoleman.phr.common.domain.usecase.patient

import cz.vvoleman.phr.base.domain.usecase.FlowUseCase
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.common.domain.repository.patient.GetSelectedPatientRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedPatientUseCase(
    private val getSelectedPatientRepository: GetSelectedPatientRepository
) : FlowUseCase<Unit?, PatientDomainModel>() {

    override fun execute(request: Unit?): Flow<PatientDomainModel> {
        return getSelectedPatientRepository.getSelectedPatient()
    }
}
