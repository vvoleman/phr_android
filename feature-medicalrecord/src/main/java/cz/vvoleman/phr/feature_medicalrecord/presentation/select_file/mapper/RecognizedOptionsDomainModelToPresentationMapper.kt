package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.mapper

import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.RecognizedOptionsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.DiagnoseDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.RecognizedOptionsPresentationModel

class RecognizedOptionsDomainModelToPresentationMapper(
    private val diagnoseMapper: DiagnoseDomainModelToPresentationMapper,
    private val patientMapper: PatientDomainModelToPresentationMapper
) {

    fun toPresentation(domainModel: RecognizedOptionsDomainModel): RecognizedOptionsPresentationModel {
        return RecognizedOptionsPresentationModel(
            visitDate = domainModel.visitDate.map { it.value },
            diagnose = domainModel.diagnose.map {  diagnoseMapper.toPresentation(it.value) },
            patient = domainModel.patient.map { patientMapper.toPresentation(it.value) }
        )
    }

}