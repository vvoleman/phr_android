package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.mapper

import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SelectedOptionsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectedOptionsPresentationModel

class SelectedOptionsPresentationToDomainMapper {

    fun toDomain(model: SelectedOptionsPresentationModel): SelectedOptionsDomainModel {
        return SelectedOptionsDomainModel(
            diagnoseId = model.diagnoseId,
            visitDate = model.visitDate,
            patientId = model.patientId
        )
    }
}
