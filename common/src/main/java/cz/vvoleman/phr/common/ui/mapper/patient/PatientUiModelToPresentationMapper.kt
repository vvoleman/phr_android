package cz.vvoleman.phr.common.ui.mapper.patient

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.ui.model.PatientUiModel

class PatientUiModelToPresentationMapper {

    fun toPresentation(patientUiModel: PatientUiModel): PatientPresentationModel {
        return PatientPresentationModel(
            id = patientUiModel.id,
            name = patientUiModel.name,
            birthDate = patientUiModel.birthDate
        )
    }

    fun toUi(patientPresentationModel: PatientPresentationModel): PatientUiModel {
        return PatientUiModel(
            id = patientPresentationModel.id,
            name = patientPresentationModel.name,
            birthDate = patientPresentationModel.birthDate
        )
    }
}
