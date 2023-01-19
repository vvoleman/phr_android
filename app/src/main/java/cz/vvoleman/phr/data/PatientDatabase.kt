package cz.vvoleman.phr.data

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.vvoleman.phr.data.address.Address
import cz.vvoleman.phr.data.diagnose.Diagnose
import cz.vvoleman.phr.data.diagnose.DiagnoseDao
import cz.vvoleman.phr.data.diagnose.DiagnoseGroup
import cz.vvoleman.phr.data.diagnose.DiagnoseGroupDao
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
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Patient::class, Facility::class, Diagnose::class, DiagnoseGroup::class, MedicalRecord::class, Medicine::class],
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
                patientDao.insertPatient(Patient(1, "Jan", "-"))
                patientDao.insertPatient(Patient(2, "Pepa", "-"))

                facilityDao.insertFacility(Facility(1, Address(
                    city="Ústí nad Labem",
                    street="Sociální péče",
                    houseNumber ="3316",
                    zipCode = "40011"
                ), "Krajská nemocnice Ústí nad Labem", "123456789", "test@usti.cz", null, null))

                facilityDao.insertFacility(Facility(2, Address(
                    city="Děčín",
                    street="U Nemocnice",
                    houseNumber ="1",
                    zipCode = "40502"
                ), "Nemocnice Děčín", "654321879", "test@decin.cz", null, null))

                diagnoseGroupDao.insert(DiagnoseGroup("I", "Jiné a neurčené infekční nemoci"))
                diagnoseGroupDao.insert(DiagnoseGroup("XXII", "Rezistence na protinádorové léky"))

                diagnoseDao.insertDiagnose(Diagnose("A00", "Cholera", "I"))
                diagnoseDao.insertDiagnose(Diagnose("U071", "COVID–19, virus laboratorně prokázán", "XXII"))

                val calendarA = Calendar.getInstance()
                calendarA.set(2020, 0, 1)
                val calendarB = Calendar.getInstance()
                calendarB.set(2020, 0, 8)

                medicalRecordDao.insertMedicalRecord(MedicalRecord(1, 1, 1, "A00", calendarA.time, "Pacient má choleru"))
                medicalRecordDao.insertMedicalRecord(MedicalRecord(2, 2, 2, "U071", calendarB.time, "Je to COVID-19"))
            }
        }
    }

    companion object {
        const val TAG = "PatientDatabase"
    }

}