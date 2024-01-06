package cz.vvoleman.phr.common.presentation.mapper.problemCategory

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel

class ProblemCategoryPresentationModelToDomainMapper {

    fun toDomain(model: ProblemCategoryPresentationModel): ProblemCategoryDomainModel {
        return ProblemCategoryDomainModel(
            id = model.id,
            name = model.name,
            color = model.color,
            createdAt = model.createdAt,
            patientId = model.patientId,
            isDefault = model.isDefault
        )
    }

    fun toPresentation(model: ProblemCategoryDomainModel): ProblemCategoryPresentationModel {
        return ProblemCategoryPresentationModel(
            id = model.id,
            name = model.name,
            color = model.color,
            createdAt = model.createdAt,
            patientId = model.patientId,
            isDefault = model.isDefault
        )
    }
}
