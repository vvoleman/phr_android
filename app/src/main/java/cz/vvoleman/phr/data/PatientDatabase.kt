package cz.vvoleman.phr.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.vvoleman.phr.data.address.Address
import cz.vvoleman.phr.data.diagnose.Diagnose
import cz.vvoleman.phr.data.diagnose.DiagnoseDao
import cz.vvoleman.phr.data.facility.Facility
import cz.vvoleman.phr.data.facility.FacilityDao
import cz.vvoleman.phr.data.medical_records.MedicalRecord
import cz.vvoleman.phr.data.medical_records.MedicalRecordDao
import cz.vvoleman.phr.data.medicine.Medicine
import cz.vvoleman.phr.data.patient.Patient
import cz.vvoleman.phr.data.patient.PatientDao
import cz.vvoleman.phr.di.ApplicationScope
import cz.vvoleman.phr.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Patient::class, Facility::class, Diagnose::class, MedicalRecord::class, Medicine::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PatientDatabase : RoomDatabase() {

    abstract fun diagnoseDao(): DiagnoseDao

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
            val db = database.get()
            val patientDao: PatientDao = db.patientDao()
            val facilityDao: FacilityDao = db.facilityDao()
            val diagnoseDao: DiagnoseDao = db.diagnoseDao()
            val medicalRecordDao: MedicalRecordDao = db.medicalRecordDao()

            applicationScope.launch {
                patientDao.insertPatient(Patient(1, "Jan", "-"))
                patientDao.insertPatient(Patient(2, "Pepa", "-"))

                facilityDao.insertFacility(Facility(1, Address(
                    id=1,
                    city="Ústí nad Labem",
                    street="Sociální péče",
                    houseNumber ="3316",
                    zipCode = "40011"
                ), "Krajská nemocnice Ústí nad Labem", "123456789", "test@usti.cz", null, null))

                facilityDao.insertFacility(Facility(1, Address(
                    id=1,
                    city="Děčín",
                    street="U Nemocnice",
                    houseNumber ="1",
                    zipCode = "40502"
                ), "Nemocnice Děčín", "654321879", "test@decin.cz", null, null))

                diagnoseDao.insertDiagnose(Diagnose(1, "Zápal plic"))
                diagnoseDao.insertDiagnose(Diagnose(2, "Chřipka"))
                diagnoseDao.insertDiagnose(Diagnose(3, "Zlomená noha"))
                diagnoseDao.insertDiagnose(Diagnose(4, "Rýma"))

                val facilityA = facilityDao.getFacilityById(1)
                val facilityB = facilityDao.getFacilityById(2)
                val diagnoseA = diagnoseDao.getDiagnoseById(1)
                val diagnoseB = diagnoseDao.getDiagnoseById(2)
                val patientA = patientDao.getPatientById(1)
                val patientB = patientDao.getPatientById(2)

                medicalRecordDao.insertMedicalRecord(MedicalRecord(1, facilityA, patientA, diagnoseA, "2020-01-01", "Pacient má zápal plic"))
                medicalRecordDao.insertMedicalRecord(MedicalRecord(2, facilityB, patientB, diagnoseB, "2020-01-02", "Pacient má chřipku"))
            }
        }
    }

}