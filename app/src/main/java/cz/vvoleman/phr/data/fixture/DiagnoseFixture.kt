package cz.vvoleman.phr.data.fixture

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseGroupDataSourceModel

class DiagnoseFixture(
    private val diagnoseDao: DiagnoseDao,
    private val diagnoseGroups: List<DiagnoseGroupDataSourceModel>
) {

    suspend fun setup(): List<DiagnoseDataSourceModel> {
        val diagnoses = getData()

        diagnoses.forEach {
            diagnoseDao.insert(it)
        }

        return diagnoses
    }

    private fun getData(): List<DiagnoseDataSourceModel> {
        val diagnoses = mutableListOf<DiagnoseDataSourceModel>()

        diagnoseGroups.getOrNull(0)?.let {
            diagnoses.add(DiagnoseDataSourceModel("A00", "Cholera", it.id))
        }

        diagnoseGroups.getOrNull(1)?.let {
            diagnoses.add(DiagnoseDataSourceModel("U071", "COVID–19, virus laboratorně prokázán", it.id))
        }

        return diagnoses
    }
}
