package cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.mapper

import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.exception.MissingFieldsException
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditViewState

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
