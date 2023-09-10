package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.diagnose.DiagnoseDataSourceApiModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel

class DiagnoseApiModelToDbMapper {

    fun toDb(diagnoseApiModel: DiagnoseDataSourceApiModel): DiagnoseDataSourceModel {
        return DiagnoseDataSourceModel(
            id = diagnoseApiModel.id,
            name = diagnoseApiModel.name,
            parent = diagnoseApiModel.parent.id
        )
    }
}
