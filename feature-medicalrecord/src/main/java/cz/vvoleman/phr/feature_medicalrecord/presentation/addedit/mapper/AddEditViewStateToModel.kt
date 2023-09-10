package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper

import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.exception.MissingFieldsException
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditPresentationModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState

class AddEditViewStateToModel {

    fun toModel(viewState: AddEditViewState): AddEditPresentationModel {
        try {
            return AddEditPresentationModel(
                createdAt = viewState.createdAt!!,
                diagnoseId = viewState.diagnoseId!!,
                problemCategoryId = viewState.problemCategoryId!!,
                patientId = viewState.patientId!!,
                medicalWorkerId = viewState.medicalWorkerId!!,
                visitDate = viewState.visitDate!!
            )
        } catch (e: NullPointerException) {
            throw MissingFieldsException(e)
        }
    }
}
