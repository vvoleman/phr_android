package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.AddEditDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditPresentationModel

class AddEditPresentationModelToDomainMapper(
    private val assetPresentationToDomainModel: AssetPresentationToDomainModelMapper
) {

    fun toDomain(model: AddEditPresentationModel): AddEditDomainModel {
        return AddEditDomainModel(
            id = model.recordId,
            createdAt = model.createdAt,
            diagnoseId = model.diagnoseId,
            problemCategoryId = model.problemCategoryId,
            patientId = model.patientId,
            medicalWorkerId = model.medicalWorkerId,
            visitDate = model.visitDate,
            files = model.assets.map { assetPresentationToDomainModel.toDomain(it) }
        )
    }
}
