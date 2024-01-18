package cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.addEdit

import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseGroupDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.DiagnoseGroupPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.DiagnosePresentationModel

class DiagnosePresentationModelToDomainMapper {

    fun toPresentation(model: DiagnoseDomainModel): DiagnosePresentationModel {
        return DiagnosePresentationModel(
            id = model.id,
            name = model.name,
            parent = DiagnoseGroupPresentationModel(
                id = model.parent.id,
                name = model.parent.name
            )
        )
    }

    fun toDomain(model: DiagnosePresentationModel): DiagnoseDomainModel {
        return DiagnoseDomainModel(
            id = model.id,
            name = model.name,
            parent = DiagnoseGroupDomainModel(
                id = model.parent.id,
                name = model.parent.name
            )
        )
    }
}
