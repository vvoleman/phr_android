package cz.vvoleman.phr.util.ocr.record.field

import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.data.room.patient.PatientDao
import cz.vvoleman.phr.util.ocr.record.RecognizedOptions
import cz.vvoleman.phr.util.ocr.record.field.result.PatientFieldResult
import cz.vvoleman.phr.util.ocr.text_processor.FieldProcessor
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PatientFieldProcessor @Inject constructor(
    private val patientDao: PatientDao
) : FieldProcessor<PatientFieldResult> {

    private var options: RecognizedOptions? = null

    override suspend fun process(text: Text): List<PatientFieldResult> {
        val patients = mutableListOf<PatientFieldResult>()
        options?.let { opts ->
            val dates = opts.visitDate

            for (date in dates) {
                val list = patientDao.getPatientByBirthDate(date.value).first()
                for (patient in list) {
                    patients.add(PatientFieldResult(patient.toPatient()))
                }
            }
        }

        return patients
    }

    fun setOptions(options: RecognizedOptions) {
        this.options = options
    }
}
