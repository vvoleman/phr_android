package cz.vvoleman.phr.feature_medicalrecord.data.mapper

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel

class DiagnoseDataSourceToDomainMapper {

    fun toDomain(diagnose: DiagnoseDataSourceModel): DiagnoseDomainModel {
        return DiagnoseDomainModel(
            id = diagnose.id,
            name = diagnose.name,
            parent = diagnose.parent
        )
    }
}
