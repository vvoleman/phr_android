package cz.vvoleman.phr.data

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.facility.MedicalFacilityDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDao
import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.data.fixture.DiagnoseFixture
import cz.vvoleman.phr.data.fixture.DiagnoseGroupFixture
import cz.vvoleman.phr.data.fixture.MedicalWorkerFixture
import cz.vvoleman.phr.data.fixture.PatientFixture
import cz.vvoleman.phr.data.fixture.ProblemCategoryFixture
import cz.vvoleman.phr.data.fixture.UnitGroupFixture
import cz.vvoleman.phr.di.ApplicationScope
import cz.vvoleman.phr.featureEvent.data.datasource.room.EventDao
import cz.vvoleman.phr.featureEvent.data.datasource.room.EventDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupEntryDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupEntryDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupScheduleItemDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupScheduleItemDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.field.NumericFieldDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.field.NumericFieldDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.unit.UnitGroupDao
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.unit.UnitGroupDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.asset.MedicalRecordAssetDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseGroupDao
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.diagnose.DiagnoseGroupDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.MedicineDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.ProductFormDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.SubstanceDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.MedicineDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.ProductFormDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.dao.SubstanceDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.MedicineScheduleDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.ScheduleItemDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.MedicineScheduleDao
import cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule.dao.ScheduleItemDao
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
        SpecificMedicalWorkerDataSourceModel::class,
        MedicalServiceDataSourceModel::class,
        MedicalFacilityDataSourceModel::class,
        MedicalRecordAssetDataSourceModel::class,
        MedicineDataSourceModel::class,
        ProductFormDataSourceModel::class,
        SubstanceDataSourceModel::class,
        MedicineScheduleDataSourceModel::class,
        ScheduleItemDataSourceModel::class,

        // Measurements
        MeasurementGroupDataSourceModel::class,
        MeasurementGroupScheduleItemDataSourceModel::class,
        MeasurementGroupEntryDataSourceModel::class,
        UnitGroupDataSourceModel::class,
        NumericFieldDataSourceModel::class,

        // Event
        EventDataSourceModel::class,
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

    abstract fun specificMedicalWorkerDao(): SpecificMedicalWorkerDao

    abstract fun medicalServiceDao(): MedicalServiceDao

    abstract fun medicalFacilityDao(): MedicalFacilityDao

    abstract fun medicalRecordAssetDao(): MedicalRecordAssetDao

    abstract fun medicineDao(): MedicineDao

    abstract fun medicineScheduleDao(): MedicineScheduleDao

    abstract fun scheduleItemDao(): ScheduleItemDao

    abstract fun productFormDao(): ProductFormDao

    abstract fun substanceDao(): SubstanceDao

    abstract fun patientDao(): PatientDao

    // Measurements
    abstract fun measurementGroupDao(): MeasurementGroupDao

    abstract fun measurementGroupScheduleItemDao(): MeasurementGroupScheduleItemDao

    abstract fun measurementGroupEntryDao(): MeasurementGroupEntryDao

    abstract fun unitGroupDao(): UnitGroupDao

    abstract fun numericFieldTypeDao(): NumericFieldDao

    // Event
    abstract fun eventDao(): EventDao

    class Callback @Inject constructor(
        private val database: Provider<PatientDatabase>,
        private val patientDataStore: PatientDataStore,
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
                patientDataStore.updatePatient(patients.first().id.toString())

                val groups = diagnoseGroupFixture.setup()

                val diagnoseFixture = DiagnoseFixture(database.diagnoseDao(), groups)
                val diagnoses = diagnoseFixture.setup()

                val problemCategoryFixture = ProblemCategoryFixture(database.problemCategoryDao(), patients)
                val problemCategories = problemCategoryFixture.setup()

                val medicalWorkerFixture = MedicalWorkerFixture(database.medicalWorkerDao())
                val medicalWorkers = medicalWorkerFixture.setup()

                val unitGroupFixture = UnitGroupFixture(database.unitGroupDao())
                unitGroupFixture.setup()
//                val medicalRecordFixture = MedicalRecordFixture(
//                    medicalRecordDao,
//                    patients,
//                    diagnoses,
//                    problemCategories,
//                    medicalWorkers
//                )
//                medicalRecordFixture.setup()
            }
        }
    }

    companion object {
        const val TAG = "PatientDatabase"
    }
}
