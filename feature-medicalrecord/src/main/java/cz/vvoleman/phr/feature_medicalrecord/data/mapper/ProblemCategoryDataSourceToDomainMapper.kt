package cz.vvoleman.phr.feature_medicalrecord.data.mapper

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel

class ProblemCategoryDataSourceToDomainMapper {

    fun toDomain(problemCategory: ProblemCategoryDataSourceModel): ProblemCategoryDomainModel {
        return ProblemCategoryDomainModel(
            id = problemCategory.id.toString(),
            name = problemCategory.name,
            color = problemCategory.color,
            patientId = problemCategory.patient_id.toString()
        )
    }

}