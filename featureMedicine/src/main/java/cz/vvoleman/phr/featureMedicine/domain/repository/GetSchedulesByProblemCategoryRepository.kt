package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

interface GetSchedulesByProblemCategoryRepository {

    suspend fun getSchedulesByProblemCategory(
        problemCategories: List<String>
    ): Map<String, List<MedicineScheduleDomainModel>>

    suspend fun getSchedulesByProblemCategory(
        problemCategory: String,
    ): List<MedicineScheduleDomainModel>
}
