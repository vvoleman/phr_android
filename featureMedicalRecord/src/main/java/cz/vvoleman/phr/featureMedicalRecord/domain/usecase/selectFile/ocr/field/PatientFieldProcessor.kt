package cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.field

import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByBirthDateRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.RecognizedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.TextDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.result.PatientFieldResultDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.ocr.FieldProcessor

class PatientFieldProcessor(
    private val getPatientByBirthDateRepository: GetPatientByBirthDateRepository
) : FieldProcessor<PatientFieldResultDomainModel> {

    private var options: RecognizedOptionsDomainModel? = null

    override suspend fun process(text: TextDomainModel): List<PatientFieldResultDomainModel> {
        val patients = mutableListOf<PatientFieldResultDomainModel>()
        options?.let { opts ->
            val dates = opts.visitDate

            for (date in dates) {
                val list = getPatientByBirthDateRepository.getPatientByBirthDate(date.value)
                for (patient in list) {
                    patients.add(PatientFieldResultDomainModel(patient))
                }
            }
        }

        return patients
    }

    fun setOptions(options: RecognizedOptionsDomainModel) {
        this.options = options
    }
}
