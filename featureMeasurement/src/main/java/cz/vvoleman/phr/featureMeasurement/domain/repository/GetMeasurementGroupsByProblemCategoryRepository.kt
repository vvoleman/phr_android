package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

interface GetMeasurementGroupsByProblemCategoryRepository {

    suspend fun getMeasurementGroupsByProblemCategory(problemCategoryId: String): List<MeasurementGroupDomainModel>

    suspend fun getMeasurementGroupsByProblemCategory(
        problemCategoryIds: List<String>
    ): Map<String, List<MeasurementGroupDomainModel>>
}
