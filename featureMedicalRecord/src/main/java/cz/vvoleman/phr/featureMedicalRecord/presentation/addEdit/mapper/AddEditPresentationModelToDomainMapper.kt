package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.AddEditDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditPresentationModel

class AddEditPresentationModelToDomainMapper(
    private val assetPresentationToDomainModel: AssetPresentationToDomainModelMapper,
    private val diagnoseMapper: DiagnosePresentationModelToDomainMapper,
) {

    fun toDomain(model: AddEditPresentationModel): AddEditDomainModel {
        return AddEditDomainModel(
            id = model.recordId,
            createdAt = model.createdAt,
            diagnose = model.diagnose?.let { diagnoseMapper.toDomain(it) },
            problemCategoryId = model.problemCategoryId,
            patientId = model.patientId,
            specificMedicalWorkerId = model.specificMedicalWorker,
            visitDate = model.visitDate,
            files = model.assets.map { assetPresentationToDomainModel.toDomain(it) }
        )
    }
}
