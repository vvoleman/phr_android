package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SelectedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.DiagnoseDomainModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model.SelectedOptionsPresentationModel

// Leaving this here, not sure what's it for
class SelectedOptionsDomainModelToPresentationMapper(
    private val patientDomainModelToPresentationMapper: PatientDomainModelToPresentationMapper,
    private val diagnoseDomainModelToPresentationMapper: DiagnoseDomainModelToPresentationMapper
) {

    fun toPresentation(domainModel: SelectedOptionsDomainModel): SelectedOptionsPresentationModel {
        return SelectedOptionsPresentationModel(
            diagnoseId = domainModel.diagnoseId,
            visitDate = domainModel.visitDate,
            patientId = domainModel.patientId
        )
    }
}
