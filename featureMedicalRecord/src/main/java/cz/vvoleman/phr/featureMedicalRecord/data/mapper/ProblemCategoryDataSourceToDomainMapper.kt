package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.category.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.ProblemCategoryDomainModel

class ProblemCategoryDataSourceToDomainMapper {

    fun toDomain(problemCategory: ProblemCategoryDataSourceModel): ProblemCategoryDomainModel {
        return ProblemCategoryDomainModel(
            id = problemCategory.id.toString(),
            name = problemCategory.name,
            color = problemCategory.color,
            patientId = problemCategory.patientId.toString()
        )
    }
}
