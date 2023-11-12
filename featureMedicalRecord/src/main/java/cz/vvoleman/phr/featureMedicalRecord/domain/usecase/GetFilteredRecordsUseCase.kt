package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.common.domain.repository.GetSelectedPatientRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.FilterRequestDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.MedicalRecordFilterRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class GetFilteredRecordsUseCase(
    private val medicalRecordFilterRepository: MedicalRecordFilterRepository,
    private val getSelectedPatientRepository: GetSelectedPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<FilterRequestDomainModel, List<GroupedItemsDomainModel<MedicalRecordDomainModel>>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: FilterRequestDomainModel):
        List<GroupedItemsDomainModel<MedicalRecordDomainModel>> {
        var patientId = request.patientId

        if (patientId == null) {
            patientId = getSelectedPatientRepository.getSelectedPatient().first().id
        }
        val updatedRequest = request.copy(patientId = patientId)

        val records = medicalRecordFilterRepository.filterRecords(updatedRequest)

        return when (request.groupBy) {
            GroupByDomainModel.DATE -> byDate(records)
            GroupByDomainModel.PROBLEM_CATEGORY -> byProblemCategory(records)
            GroupByDomainModel.MEDICAL_WORKER -> byMedicalWorker(records)
        }
    }

    private fun byDate(
        records: List<MedicalRecordDomainModel>
    ): List<GroupedItemsDomainModel<MedicalRecordDomainModel>> {
        val map = mutableMapOf<String, MutableList<MedicalRecordDomainModel>>()
        val dates = mutableMapOf<String, LocalDate>()
        for (record in records) {
            val date = "${record.visitDate.year}-${record.visitDate.monthValue}"
            dates[date] = LocalDate.of(record.visitDate.year, record.visitDate.monthValue, 1)

            if (!map.containsKey(date)) {
                map[date] = mutableListOf()
            }

            map[date]?.add(record)
        }

        // Sort map by key
        return map.toSortedMap().map { GroupedItemsDomainModel(dates[it.key]!!, it.value.toList()) }
    }

    private fun byProblemCategory(records: List<MedicalRecordDomainModel>):
        List<GroupedItemsDomainModel<MedicalRecordDomainModel>> {
        val map = mutableMapOf<String, MutableList<MedicalRecordDomainModel>>()
        for (record in records) {
            val category = record.problemCategory?.name ?: "-"

            if (!map.containsKey(category)) {
                map[category] = mutableListOf()
            }

            map[category]?.add(record)
        }

        return map.map { GroupedItemsDomainModel(it.key, it.value.toList()) }
    }

    private fun byMedicalWorker(records: List<MedicalRecordDomainModel>):
        List<GroupedItemsDomainModel<MedicalRecordDomainModel>> {
        val map = mutableMapOf<String, MutableList<MedicalRecordDomainModel>>()
        for (record in records) {
            val worker = record.medicalWorker?.name ?: "-"

            if (!map.containsKey(worker)) {
                map[worker] = mutableListOf()
            }

            map[worker]?.add(record)
        }

        return map.map { GroupedItemsDomainModel(it.key, it.value.toList()) }
    }
}
