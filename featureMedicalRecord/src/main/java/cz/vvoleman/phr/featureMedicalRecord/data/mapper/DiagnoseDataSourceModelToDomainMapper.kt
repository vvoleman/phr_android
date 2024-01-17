package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseGroupDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseWithGroupDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseGroupDomainModel

class DiagnoseDataSourceModelToDomainMapper {

    fun toDomain(diagnose: DiagnoseWithGroupDataSourceModel): DiagnoseDomainModel {
        return DiagnoseDomainModel(
            id = diagnose.diagnose.id,
            name = diagnose.diagnose.name,
            parent = DiagnoseGroupDomainModel(
                id = diagnose.diagnoseGroup.id,
                name = diagnose.diagnoseGroup.name
            )
        )
    }

    fun toDataSource(model: DiagnoseDomainModel): DiagnoseWithGroupDataSourceModel {
        return DiagnoseWithGroupDataSourceModel(
            diagnose = DiagnoseDataSourceModel(
                id = model.id,
                name = model.name,
                parent = model.parent.id
            ),
            diagnoseGroup = DiagnoseGroupDataSourceModel(
                id = model.parent.id,
                name = model.parent.name
            )
        )
    }
}
