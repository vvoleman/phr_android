package cz.vvoleman.phr.data

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.vvoleman.phr.common.data.datasource.model.AddressDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.data.core.Gender
import cz.vvoleman.phr.data.core.diagnose.Diagnose
import cz.vvoleman.phr.data.room.medicine.MedicineDao
import cz.vvoleman.phr.data.room.medicine.MedicineEntity
import cz.vvoleman.phr.data.room.medicine.MedicineSubstanceCrossRef
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceDao
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceEntity
import cz.vvoleman.phr.di.ApplicationScope
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.MedicalRecordDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.asset.MedicalRecordAssetDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseGroupDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseGroupDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDao
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel
import cz.vvoleman.phr.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
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
        MedicineEntity::class,
        SubstanceEntity::class,
        MedicineSubstanceCrossRef::class,
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

    abstract fun substanceDao(): SubstanceDao

    abstract fun patientDao(): PatientDao

    class Callback @Inject constructor(
        private val database: Provider<PatientDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope,
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // Fixtures
            val database = database.get()
            val patientDao: PatientDao = database.patientDao()
            val diagnoseDao: DiagnoseDao = database.diagnoseDao()
            val medicalRecordDao: MedicalRecordDao = database.medicalRecordDao()
            val diagnoseGroupDao: DiagnoseGroupDao = database.diagnoseGroupDao()

            Log.d("PatientDatabase", "onCreate")

            applicationScope.launch {
                Log.d("PatientDatabase", "onCreate launch")

                val patientA = PatientDataSourceModel(
                    1, "Vojta", AddressDataSourceModel(
                        city = "Ústí nad Labem",
                        street = "Kollárova",
                        house_number = "226/2",
                        zip_code = "40003"
                    ), LocalDate.of(2001, 2, 7), Gender.MALE.name
                )

                patientDao.insert(patientA)

                val patientB = PatientDataSourceModel(
                    2, "Petr", AddressDataSourceModel(
                        city = "Praha",
                        street = "Masarykova",
                        house_number = "22",
                        zip_code = "10000"
                    ), LocalDate.of(2000, 1, 1), Gender.MALE.name
                )

                patientDao.insert(patientB)

                val groupA = DiagnoseGroupDataSourceModel(
                    "I", "Jiné a neurčené infekční nemoci",
                )

                val groupB = DiagnoseGroupDataSourceModel(
                    "XXII", "Rezistence na protinádorové léky",
                )

                diagnoseGroupDao.insert(groupA)
                diagnoseGroupDao.insert(groupB)

                val diagnoseA = DiagnoseDataSourceModel("A00", "Cholera", groupA.id)
                val diagnoseB = DiagnoseDataSourceModel("U071", "COVID–19, virus laboratorně prokázán", groupB.id)

                diagnoseDao.insert(diagnoseA)
                diagnoseDao.insert(diagnoseB)

//                // Default problemCategory
//                val problem = ProblemCategoryEntity.from(ProblemCategory(
//                    id = 1,
//                    name = "Výchozí",
//                    LocalDate.now(),
//                    Color.ORANGE,
//                    1,
//                ))
//
//                database.problemCategoryDao().insert(problem)

                val calendarA = Calendar.getInstance()
                calendarA.set(2020, 0, 1)
                val calendarB = Calendar.getInstance()
                calendarB.set(2020, 0, 8)

                medicalRecordDao.insert(
                    MedicalRecordDataSourceModel(
                        id=1,
                        patient_id = patientA.id!!,
                        diagnose_id = diagnoseA.id,
                    )
                )

                medicalRecordDao.insert(
                    MedicalRecordDataSourceModel(
                        id=2,
                        patient_id = patientB.id!!,
                        diagnose_id = diagnoseB.id,
                    )
                )
            }
        }

    }

    companion object {
        const val TAG = "PatientDatabase"
    }

}