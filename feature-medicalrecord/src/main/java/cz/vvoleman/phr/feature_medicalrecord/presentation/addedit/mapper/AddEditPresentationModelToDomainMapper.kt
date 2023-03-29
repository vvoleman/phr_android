package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper

import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AddEditDomainModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditPresentationModel

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