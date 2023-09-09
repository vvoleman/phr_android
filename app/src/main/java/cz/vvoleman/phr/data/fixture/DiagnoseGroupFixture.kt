package cz.vvoleman.phr.data.fixture

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseGroupDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseGroupDataSourceModel

class DiagnoseGroupFixture(private val diagnoseGroupDao: DiagnoseGroupDao) {

    suspend fun setup(): List<DiagnoseGroupDataSourceModel> {
        val diagnoseGroups = getData()

        diagnoseGroups.forEach {
            diagnoseGroupDao.insert(it)
        }

        return diagnoseGroups
    }

    private fun getData(): List<DiagnoseGroupDataSourceModel> {
        return listOf(
            DiagnoseGroupDataSourceModel(
                "I",
                "Jiné a neurčené infekční nemoci"
            ),
            DiagnoseGroupDataSourceModel(
                "XXII",
                "Rezistence na protinádorové léky"
            )
        )
    }
}
