package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.retrofit.diagnose.DiagnoseDataSourceApiModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseGroupDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseWithGroupDataSourceModel

class DiagnoseApiModelToDbMapper {

    fun toDb(diagnoseApiModel: DiagnoseDataSourceApiModel): DiagnoseWithGroupDataSourceModel {
        return DiagnoseWithGroupDataSourceModel(
            diagnose = DiagnoseDataSourceModel(
                id = diagnoseApiModel.id,
                name = diagnoseApiModel.name,
                parent = diagnoseApiModel.parent.id
            ),
            diagnoseGroup = DiagnoseGroupDataSourceModel(
                id = diagnoseApiModel.parent.id,
                name = diagnoseApiModel.parent.name
            )
        )
    }
}
