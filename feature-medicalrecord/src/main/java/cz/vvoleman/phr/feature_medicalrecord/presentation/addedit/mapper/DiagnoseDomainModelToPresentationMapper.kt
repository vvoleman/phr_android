package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper

import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.DiagnosePresentationModel

class DiagnoseDomainModelToPresentationMapper {

    fun toPresentation(domainModel: DiagnoseDomainModel): DiagnosePresentationModel {
        return DiagnosePresentationModel(
            id = domainModel.id,
            name = domainModel.name,
            parent = domainModel.parent
        )
    }
}
