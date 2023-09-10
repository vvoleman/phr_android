package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel

class DiagnoseDataSourceToDomainMapper {

    fun toDomain(diagnose: DiagnoseDataSourceModel): DiagnoseDomainModel {
        return DiagnoseDomainModel(
            id = diagnose.id,
            name = diagnose.name,
            parent = diagnose.parent
        )
    }
}
