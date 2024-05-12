package cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.core

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.SpecificMedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.addEdit.DiagnosePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordPresentationModel

class MedicalRecordPresentationModelToDomainMapper(
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper,
    private val diagnoseMapper: DiagnosePresentationModelToDomainMapper,
    private val specificMedicalWorkerMapper: SpecificMedicalWorkerPresentationModelToDomainMapper,
    private val assetMapper: MedicalRecordAssetPresentationModelToDomainMapper
) {

    fun toDomain(model: MedicalRecordPresentationModel): MedicalRecordDomainModel {
        return MedicalRecordDomainModel(
            id = model.id,
            patient = patientMapper.toDomain(model.patient),
            problemCategory = model.problemCategory?.let { problemCategoryMapper.toDomain(it) },
            diagnose = model.diagnose?.let { diagnoseMapper.toDomain(it) },
            specificMedicalWorker = model.specificMedicalWorker?.let { specificMedicalWorkerMapper.toDomain(it) },
            assets = model.assets.map { assetMapper.toDomain(it) },
            createdAt = model.createdAt,
            visitDate = model.visitDate,
        )
    }

    fun toPresentation(model: MedicalRecordDomainModel): MedicalRecordPresentationModel {
        return MedicalRecordPresentationModel(
            id = model.id,
            patient = patientMapper.toPresentation(model.patient),
            problemCategory = model.problemCategory?.let { problemCategoryMapper.toPresentation(it) },
            diagnose = model.diagnose?.let { diagnoseMapper.toPresentation(it) },
            specificMedicalWorker = model.specificMedicalWorker?.let { specificMedicalWorkerMapper.toPresentation(it) },
            assets = model.assets.map { assetMapper.toPresentation(it) },
            createdAt = model.createdAt,
            visitDate = model.visitDate,
        )
    }
}
