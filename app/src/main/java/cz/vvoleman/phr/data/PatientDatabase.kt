package cz.vvoleman.phr.data

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.vvoleman.phr.data.core.Address
import cz.vvoleman.phr.data.core.Gender
import cz.vvoleman.phr.data.core.Patient
import cz.vvoleman.phr.data.diagnose.Diagnose
import cz.vvoleman.phr.data.diagnose.DiagnoseDao
import cz.vvoleman.phr.data.diagnose.DiagnoseGroup
import cz.vvoleman.phr.data.diagnose.DiagnoseGroupDao
import cz.vvoleman.phr.data.facility.Facility
import cz.vvoleman.phr.data.facility.FacilityDao
import cz.vvoleman.phr.data.medical_records.MedicalRecord
import cz.vvoleman.phr.data.medical_records.MedicalRecordDao
import cz.vvoleman.phr.data.medicine.Medicine
import cz.vvoleman.phr.data.room.address.AddressEntity
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
    entities = [PatientEntity::class, Facility::class, Diagnose::class, DiagnoseGroup::class, MedicalRecord::class, Medicine::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PatientDatabase : RoomDatabase() {

    abstract fun diagnoseDao(): DiagnoseDao

    abstract fun diagnoseGroupDao(): DiagnoseGroupDao

    abstract fun facilityDao(): FacilityDao

    abstract fun medicalRecordDao(): MedicalRecordDao

    abstract fun patientDao(): PatientDao

    class Callback @Inject constructor(
        private val database: Provider<PatientDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
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
                patientDao.insertPatient(
                    PatientEntity.from(
                        Patient(
                            1, "Vojta", Address(
                                city = "Ústí nad Labem",
                                street = "Kollárova",
                                houseNumber = "226/2",
                                zipCode = "40003"
                            ), LocalDate.of(2001, 2, 7), Gender.MALE
                        )
                    )
                )

                patientDao.insertPatient(
                    PatientEntity.from(
                        Patient(
                            2, "Petr", Address(
                                city = "Praha",
                                street = "Masarykova",
                                houseNumber = "22",
                                zipCode = "10000"
                            ), LocalDate.of(2000, 1, 1), Gender.MALE
                        )
                    )
                )

                facilityDao.insertFacility(
                    Facility(
                        1,
                        AddressEntity.from(Address(
                            city = "Ústí nad Labem",
                            street = "Sociální péče",
                            houseNumber = "3316",
                            zipCode = "4001"
                        )),
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
                        AddressEntity.from(Address(
                            city = "Děčín",
                            street = "U Nemocnice",
                            houseNumber = "1",
                            zipCode = "40502"
                        )),
                        "Nemocnice Děčín",
                        "654321879",
                        "test@decin.cz",
                        null,
                        null
                    )
                )

                diagnoseGroupDao.insert(
                    DiagnoseGroup(
                        "I",
                        "Jiné a neurčené infekční nemoci"
                    )
                )
                diagnoseGroupDao.insert(
                    DiagnoseGroup(
                        "XXII",
                        "Rezistence na protinádorové léky"
                    )
                )

                diagnoseDao.insertDiagnose(Diagnose("A00", "Cholera", "I"))
                diagnoseDao.insertDiagnose(
                    Diagnose(
                        "U071",
                        "COVID–19, virus laboratorně prokázán",
                        "XXII"
                    )
                )

                val calendarA = Calendar.getInstance()
                calendarA.set(2020, 0, 1)
                val calendarB = Calendar.getInstance()
                calendarB.set(2020, 0, 8)

                medicalRecordDao.insertMedicalRecord(
                    MedicalRecord(
                        1,
                        1,
                        1,
                        "A00",
                        LocalDate.of(2020, 1, 1),
                        "Pacient má choleru"
                    )
                )
                medicalRecordDao.insertMedicalRecord(
                    MedicalRecord(
                        2,
                        2,
                        2,
                        "U071",
                        LocalDate.of(2020, 1, 8),
                        "Je to COVID-19"
                    )
                )
            }
        }
    }

    companion object {
        const val TAG = "PatientDatabase"
    }

}