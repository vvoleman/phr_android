package cz.vvoleman.phr.common.presentation.mapper.problemCategory

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryWithInfoDomainModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryWithInfoPresentationModel

class ProblemCategoryWithInfoPresentationModelToDomainMapper(
    private val problemCategoryPresentationModelToDomainMapper: ProblemCategoryPresentationModelToDomainMapper,
    private val problemCategoryInfoPresentationModelToDomainMapper: ProblemCategoryInfoPresentationModelToDomainMapper
) {

    fun toDomain(model: ProblemCategoryWithInfoPresentationModel): ProblemCategoryWithInfoDomainModel {
        return ProblemCategoryWithInfoDomainModel(
            problemCategory = problemCategoryPresentationModelToDomainMapper.toDomain(model.problemCategory),
            info = problemCategoryInfoPresentationModelToDomainMapper.toDomain(model.info)
        )
    }

    fun toPresentation(model: ProblemCategoryWithInfoDomainModel): ProblemCategoryWithInfoPresentationModel {
        return ProblemCategoryWithInfoPresentationModel(
            problemCategory = problemCategoryPresentationModelToDomainMapper.toPresentation(model.problemCategory),
            info = problemCategoryInfoPresentationModelToDomainMapper.toPresentation(model.info)
        )
    }
}
