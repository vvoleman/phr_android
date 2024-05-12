package cz.vvoleman.phr.featureMedicalRecord.ui.mapper

import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.DiagnoseGroupPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.DiagnosePresentationModel
import cz.vvoleman.phr.featureMedicalRecord.ui.model.DiagnoseGroupUiModel
import cz.vvoleman.phr.featureMedicalRecord.ui.model.DiagnoseUiModel

class DiagnoseUiModelToPresentationMapper {

    fun toPresentation(model: DiagnoseUiModel): DiagnosePresentationModel {
        return DiagnosePresentationModel(
            id = model.id,
            name = model.name,
            parent = DiagnoseGroupPresentationModel(
                id = model.parent.id,
                name = model.parent.name
            )
        )
    }

    fun toUi(model: DiagnosePresentationModel): DiagnoseUiModel {
        return DiagnoseUiModel(
            id = model.id,
            name = model.name,
            parent = DiagnoseGroupUiModel(
                id = model.parent.id,
                name = model.parent.name
            )
        )
    }
}
