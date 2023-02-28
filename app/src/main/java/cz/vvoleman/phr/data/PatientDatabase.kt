package cz.vvoleman.phr.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.vvoleman.phr.data.core.*
import cz.vvoleman.phr.data.core.diagnose.Diagnose
import cz.vvoleman.phr.data.core.diagnose.DiagnoseGroup
import cz.vvoleman.phr.data.core.medical_record.MedicalRecord
import cz.vvoleman.phr.data.facility.Facility
import cz.vvoleman.phr.data.facility.FacilityDao
import cz.vvoleman.phr.data.room.address.AddressEntity
import cz.vvoleman.phr.data.room.diagnose.DiagnoseDao
import cz.vvoleman.phr.data.room.diagnose.DiagnoseEntity
import cz.vvoleman.phr.data.room.diagnose.DiagnoseGroupDao
import cz.vvoleman.phr.data.room.diagnose.DiagnoseGroupEntity
import cz.vvoleman.phr.data.room.medical_record.asset.MedicalRecordAssetEntity
import cz.vvoleman.phr.data.room.medical_record.MedicalRecordDao
import cz.vvoleman.phr.data.room.medical_record.MedicalRecordEntity
import cz.vvoleman.phr.data.room.medical_record.asset.MedicalRecordAssetDao
import cz.vvoleman.phr.data.room.medical_record.category.ProblemCategoryDao
import cz.vvoleman.phr.data.room.medical_record.category.ProblemCategoryEntity
import cz.vvoleman.phr.data.room.medical_record.worker.MedicalWorkerDao
import cz.vvoleman.phr.data.room.medical_record.worker.MedicalWorkerEntity
import cz.vvoleman.phr.data.room.medicine.MedicineDao
import cz.vvoleman.phr.data.room.medicine.MedicineEntity
import cz.vvoleman.phr.data.room.medicine.MedicineSubstanceCrossRef
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceDao
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceEntity
import cz.vvoleman.phr.data.room.patient.PatientDao
import cz.vvoleman.phr.data.room.patient.PatientEntity
import cz.vvoleman.phr.di.ApplicationScope
import cz.vvoleman.phr.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        PatientEntity::class,
        Facility::class,
        DiagnoseEntity::class,
        DiagnoseGroupEntity::class,
        MedicalRecordEntity::class,
        ProblemCategoryEntity::class,
        MedicalWorkerEntity::class,
        MedicalRecordAssetEntity::class,
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

    abstract fun facilityDao(): FacilityDao

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
            val facilityDao: FacilityDao = database.facilityDao()
            val diagnoseDao: DiagnoseDao = database.diagnoseDao()
            val medicalRecordDao: MedicalRecordDao = database.medicalRecordDao()
            val diagnoseGroupDao: DiagnoseGroupDao = database.diagnoseGroupDao()

            applicationScope.launch {
                val patientA = Patient(
                    1, "Vojta", Address(
                        city = "Ústí nad Labem",
                        street = "Kollárova",
                        houseNumber = "226/2",
                        zipCode = "40003"
                    ), LocalDate.of(2001, 2, 7), Gender.MALE
                )

                patientDao.insertPatient(
                    PatientEntity.from(
                        patientA
                    )
                )

                val patientB = Patient(
                    2, "Petr", Address(
                        city = "Praha",
                        street = "Masarykova",
                        houseNumber = "22",
                        zipCode = "10000"
                    ), LocalDate.of(2000, 1, 1), Gender.MALE
                )

                patientDao.insertPatient(
                    PatientEntity.from(
                        patientB
                    )
                )

                facilityDao.insertFacility(
                    Facility(
                        1,
                        AddressEntity.from(
                            Address(
                                city = "Ústí nad Labem",
                                street = "Sociální péče",
                                houseNumber = "3316",
                                zipCode = "4001"
                            )
                        ),
                        "Nemocnice Ústí nad Labem",
                        "123456789",
                        "test@usti.cz",
                        null,
                        null
                    )
                )


                facilityDao.insertFacility(
                    Facility(
                        2,
                        AddressEntity.from(
                            Address(
                                city = "Děčín",
                                street = "U Nemocnice",
                                houseNumber = "1",
                                zipCode = "40502"
                            )
                        ),
                        "Nemocnice Děčín",
                        "654321879",
                        "test@decin.cz",
                        null,
                        null
                    )
                )

                val groupA = DiagnoseGroup(
                    "I", "Jiné a neurčené infekční nemoci",
                    listOf(
                        Diagnose("A00", "Cholera"),
                    )
                )

                val groupB = DiagnoseGroup(
                    "XXII", "Rezistence na protinádorové léky",
                    listOf(
                        Diagnose("U071", "COVID–19, virus laboratorně prokázán")
                    )
                )

                createGroup(diagnoseDao, diagnoseGroupDao, groupA)
                createGroup(diagnoseDao, diagnoseGroupDao, groupB)

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
                    MedicalRecordEntity.from(
                        MedicalRecord(
                            id=1,
                            patient=patientA,
                            diagnose = groupA.diagnoses[0]
                        )
                    )
                )

                medicalRecordDao.insert(
                    MedicalRecordEntity.from(
                        MedicalRecord(
                            id=2,
                            patient=patientB,
                            diagnose = groupB.diagnoses[0]
                        )
                    )
                )
            }
        }

        suspend fun createGroup(
            diagnoseDao: DiagnoseDao,
            diagnoseGroupDao: DiagnoseGroupDao,
            group: DiagnoseGroup,
            createDiagnoses: Boolean = true
        ) {
            diagnoseGroupDao.insert(DiagnoseGroupEntity.from(group))

            if (!createDiagnoses) return

            val diagnoses = mutableListOf<DiagnoseEntity>()
            for (diagnose in group.diagnoses) {
                diagnoses.add(DiagnoseEntity.from(diagnose, group))
            }

            diagnoseDao.insert(diagnoses)
        }
    }

    companion object {
        const val TAG = "PatientDatabase"
    }

}