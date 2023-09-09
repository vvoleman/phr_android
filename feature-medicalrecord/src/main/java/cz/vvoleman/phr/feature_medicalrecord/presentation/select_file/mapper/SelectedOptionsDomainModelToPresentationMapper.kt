package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.mapper

import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SelectedOptionsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.DiagnoseDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectedOptionsPresentationModel

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
