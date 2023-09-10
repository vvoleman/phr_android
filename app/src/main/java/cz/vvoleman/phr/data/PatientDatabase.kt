package cz.vvoleman.phr.data

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.data.fixture.DiagnoseFixture
import cz.vvoleman.phr.data.fixture.DiagnoseGroupFixture
import cz.vvoleman.phr.data.fixture.MedicalRecordFixture
import cz.vvoleman.phr.data.fixture.MedicalWorkerFixture
import cz.vvoleman.phr.data.fixture.PatientFixture
import cz.vvoleman.phr.data.fixture.ProblemCategoryFixture
import cz.vvoleman.phr.di.ApplicationScope
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.category.ProblemCategoryDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.category.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseGroupDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseGroupDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.worker.MedicalWorkerDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.MedicineDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.ProductFormDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.SubstanceDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.MedicineDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.ProductFormDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.SubstanceDao
import cz.vvoleman.phr.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        PatientDataSourceModel::class,
        DiagnoseDataSourceModel::class,
        DiagnoseGroupDataSourceModel::class,
        MedicalRecordDataSourceModel::class,
        ProblemCategoryDataSourceModel::class,
        MedicalWorkerDataSourceModel::class,
        MedicalRecordAssetDataSourceModel::class,
        MedicineDataSourceModel::class,
        ProductFormDataSourceModel::class,
        SubstanceDataSourceModel::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PatientDatabase : RoomDatabase() {

    abstract fun diagnoseDao(): DiagnoseDao

    abstract fun diagnoseGroupDao(): DiagnoseGroupDao

    abstract fun medicalRecordDao(): MedicalRecordDao

    abstract fun problemCategoryDao(): ProblemCategoryDao

    abstract fun medicalWorkerDao(): MedicalWorkerDao

    abstract fun medicalRecordAssetDao(): MedicalRecordAssetDao

    abstract fun medicineDao(): MedicineDao

    abstract fun productFormDao(): ProductFormDao

    abstract fun substanceDao(): SubstanceDao

    abstract fun patientDao(): PatientDao

    class Callback @Inject constructor(
        private val database: Provider<PatientDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val database = database.get()
            val medicalRecordDao: MedicalRecordDao = database.medicalRecordDao()

            Log.d(TAG, "onCreate")

            applicationScope.launch {
                Log.d(TAG, "onCreate launch")

                val patientFixture = PatientFixture(database.patientDao())
                val diagnoseGroupFixture = DiagnoseGroupFixture(database.diagnoseGroupDao())

                val patients = patientFixture.setup()
                val groups = diagnoseGroupFixture.setup()

                val diagnoseFixture = DiagnoseFixture(database.diagnoseDao(), groups)
                val diagnoses = diagnoseFixture.setup()

                val problemCategoryFixture = ProblemCategoryFixture(database.problemCategoryDao(), patients)
                val problemCategories = problemCategoryFixture.setup()

                val medicalWorkerFixture = MedicalWorkerFixture(database.medicalWorkerDao())
                val medicalWorkers = medicalWorkerFixture.setup()

                val medicalRecordFixture = MedicalRecordFixture(
                    medicalRecordDao,
                    patients,
                    diagnoses,
                    problemCategories,
                    medicalWorkers
                )
                medicalRecordFixture.setup()
            }
        }
    }

    companion object {
        const val TAG = "PatientDatabase"
    }
}
