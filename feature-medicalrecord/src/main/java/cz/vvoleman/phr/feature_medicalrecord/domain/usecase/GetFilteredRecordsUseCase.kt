package cz.vvoleman.phr.feature_medicalrecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.FilterRequestDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupedMedicalRecordsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.MedicalRecordFilterRepository
import java.time.LocalDate

class GetFilteredRecordsUseCase(
    private val medicalRecordFilterRepository: MedicalRecordFilterRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<FilterRequestDomainModel, List<GroupedMedicalRecordsDomainModel>>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: FilterRequestDomainModel): List<GroupedMedicalRecordsDomainModel> {
        val records = medicalRecordFilterRepository.filterRecords(request)

        return when (request.groupBy) {
            GroupByDomainModel.DATE -> byDate(records)
            GroupByDomainModel.PROBLEM_CATEGORY -> byProblemCategory(records)
            GroupByDomainModel.MEDICAL_WORKER -> byMedicalWorker(records)
        }
    }

    private fun byDate(records: List<MedicalRecordDomainModel>): List<GroupedMedicalRecordsDomainModel> {
        val map = mutableMapOf<String, MutableList<MedicalRecordDomainModel>>()
        val dates = mutableMapOf<String, LocalDate>()
        for (record in records) {
            val date = "${record.createdAt.year}-${record.createdAt.monthValue}"
            dates[date] = LocalDate.of(record.createdAt.year, record.createdAt.monthValue, 1)

            if (!map.containsKey(date)) {
                map[date] = mutableListOf(record)
            }

            map[date]?.add(record)
        }

        return map.map { GroupedMedicalRecordsDomainModel(dates[it.key]!!, it.value.toList()) }
    }

    private fun byProblemCategory(records: List<MedicalRecordDomainModel>): List<GroupedMedicalRecordsDomainModel> {
        val map = mutableMapOf<String, MutableList<MedicalRecordDomainModel>>()
        for (record in records) {
            val category = record.problemCategory?.name ?: "-"

            if (!map.containsKey(category)) {
                map[category] = mutableListOf(record)
            }

            map[category]?.add(record)
        }

        return map.map { GroupedMedicalRecordsDomainModel(it.key, it.value.toList()) }
    }

    private fun byMedicalWorker(records: List<MedicalRecordDomainModel>): List<GroupedMedicalRecordsDomainModel> {
        val map = mutableMapOf<String, MutableList<MedicalRecordDomainModel>>()
        for (record in records) {
            val worker = record.medicalWorker?.name ?: "-"

            if (!map.containsKey(worker)) {
                map[worker] = mutableListOf(record)
            }

            map[worker]?.add(record)
        }

        return map.map { GroupedMedicalRecordsDomainModel(it.key, it.value.toList()) }
    }

}