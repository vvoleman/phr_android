package cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.selectFile

import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SelectedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile.SelectedOptionsPresentationModel

class SelectedOptionsPresentationToDomainMapper {

    fun toDomain(model: SelectedOptionsPresentationModel): SelectedOptionsDomainModel {
        return SelectedOptionsDomainModel(
            diagnoseId = model.diagnoseId,
            visitDate = model.visitDate,
            patientId = model.patientId
        )
    }
}
