package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.RecognizedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.DiagnoseDomainModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model.RecognizedOptionsPresentationModel

class RecognizedOptionsDomainModelToPresentationMapper(
    private val diagnoseMapper: DiagnoseDomainModelToPresentationMapper,
    private val patientMapper: PatientDomainModelToPresentationMapper
) {

    fun toPresentation(domainModel: RecognizedOptionsDomainModel): RecognizedOptionsPresentationModel {
        return RecognizedOptionsPresentationModel(
            visitDate = domainModel.visitDate.map { it.value },
            diagnose = domainModel.diagnose.map { diagnoseMapper.toPresentation(it.value) },
            patient = domainModel.patient.map { patientMapper.toPresentation(it.value) }
        )
    }
}
