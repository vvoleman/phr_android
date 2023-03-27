package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.field

import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.result.PatientFieldResultDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.RecognizedOptionsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.TextDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.GetPatientByBirthDateRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.ocr.FieldProcessor

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