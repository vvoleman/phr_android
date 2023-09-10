package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.mapper

import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SelectedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model.SelectedOptionsPresentationModel

class SelectedOptionsPresentationToDomainMapper {

    fun toDomain(model: SelectedOptionsPresentationModel): SelectedOptionsDomainModel {
        return SelectedOptionsDomainModel(
            diagnoseId = model.diagnoseId,
            visitDate = model.visitDate,
            patientId = model.patientId
        )
    }
}
