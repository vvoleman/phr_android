package cz.vvoleman.phr.common.data.mapper.problemCategory

import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.common.domain.model.problemCategory.request.SaveProblemCategoryRequest
import java.time.LocalDateTime

class ProblemCategoryDataSourceModelToSaveProblemCategoryRequestMapper {

    fun toDataSource(model: SaveProblemCategoryRequest): ProblemCategoryDataSourceModel {
        return ProblemCategoryDataSourceModel(
            id = model.id?.toInt(),
            name = model.name,
            createdAt = model.createdAt ?: LocalDateTime.now(),
            color = model.color,
            patientId = model.patientId.toInt(),
            isDefault = model.isDefault,
        )
    }

}
