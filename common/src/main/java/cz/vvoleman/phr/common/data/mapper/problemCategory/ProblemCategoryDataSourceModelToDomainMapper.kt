package cz.vvoleman.phr.common.data.mapper.problemCategory

import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

class ProblemCategoryDataSourceModelToDomainMapper {

    fun toDomain(model: ProblemCategoryDataSourceModel): ProblemCategoryDomainModel {
        return ProblemCategoryDomainModel(
            id = model.id.toString(),
            name = model.name,
            color = model.color,
            createdAt = model.createdAt,
            patientId = model.patientId.toString()
        )
    }

    fun toDataSource(model: ProblemCategoryDomainModel): ProblemCategoryDataSourceModel {
        return ProblemCategoryDataSourceModel(
            id = model.id.toInt(),
            name = model.name,
            color = model.color,
            createdAt = model.createdAt,
            patientId = model.patientId.toInt()
        )
    }
}
