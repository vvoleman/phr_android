package cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.selectFile

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SelectedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.addEdit.DiagnosePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile.SelectedOptionsPresentationModel

@Suppress("UnusedPrivateProperty")
class SelectedOptionsDomainModelToPresentationMapper(
    private val patientPresentationModelToDomainMapper: PatientPresentationModelToDomainMapper,
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
