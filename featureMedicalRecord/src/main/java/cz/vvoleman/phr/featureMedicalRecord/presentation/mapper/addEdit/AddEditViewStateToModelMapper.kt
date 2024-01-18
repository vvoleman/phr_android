package cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.addEdit

import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditViewState
import java.time.LocalDate

class AddEditViewStateToModelMapper {

    fun toModel(viewState: AddEditViewState): AddEditPresentationModel {
        return AddEditPresentationModel(
            recordId = viewState.recordId,
            createdAt = viewState.createdAt ?: LocalDate.now(),
            diagnose = viewState.diagnose,
            problemCategoryId = viewState.problemCategoryId,
            patientId = viewState.patientId!!,
            specificMedicalWorker = viewState.specificMedicalWorkerId,
            visitDate = viewState.visitDate!!,
            assets = viewState.assets
        )
    }
}
