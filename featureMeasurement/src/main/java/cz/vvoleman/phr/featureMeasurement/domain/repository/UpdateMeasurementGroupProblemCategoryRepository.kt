package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

interface UpdateMeasurementGroupProblemCategoryRepository {

    suspend fun updateMeasurementGroupProblemCategory(
        measurementGroup: MeasurementGroupDomainModel,
        problemCategory: ProblemCategoryDomainModel?
    )

}
