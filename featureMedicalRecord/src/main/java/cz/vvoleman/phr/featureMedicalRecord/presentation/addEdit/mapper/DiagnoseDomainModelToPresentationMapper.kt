package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.DiagnosePresentationModel

class DiagnoseDomainModelToPresentationMapper {

    fun toPresentation(domainModel: DiagnoseDomainModel): DiagnosePresentationModel {
        return DiagnosePresentationModel(
            id = domainModel.id,
            name = domainModel.name,
            parent = domainModel.parent
        )
    }
}
