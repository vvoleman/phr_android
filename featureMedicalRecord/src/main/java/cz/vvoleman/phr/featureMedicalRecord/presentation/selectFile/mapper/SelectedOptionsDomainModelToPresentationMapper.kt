package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SelectedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.DiagnosePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model.SelectedOptionsPresentationModel

@Suppress("UnusedPrivateProperty")
class SelectedOptionsDomainModelToPresentationMapper(
    private val patientDomainModelToPresentationMapper: PatientDomainModelToPresentationMapper,
    private val diagnosePresentationModelToDomainMapper: DiagnosePresentationModelToDomainMapper
) {

    fun toPresentation(domainModel: SelectedOptionsDomainModel): SelectedOptionsPresentationModel {
        return SelectedOptionsPresentationModel(
            diagnoseId = domainModel.diagnoseId,
            visitDate = domainModel.visitDate,
            patientId = domainModel.patientId
        )
    }
}
