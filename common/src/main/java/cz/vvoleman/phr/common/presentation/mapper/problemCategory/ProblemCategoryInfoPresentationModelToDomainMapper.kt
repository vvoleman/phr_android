package cz.vvoleman.phr.common.presentation.mapper.problemCategory

import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryInfoDomainModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.AdditionalInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel

class ProblemCategoryInfoPresentationModelToDomainMapper(
    private val problemCategoryPresentationModelToDomainMapper: ProblemCategoryPresentationModelToDomainMapper,
) {

    fun toDomain(model: ProblemCategoryInfoPresentationModel): ProblemCategoryInfoDomainModel {
        return ProblemCategoryInfoDomainModel(
            mainSlot = model.mainSlot,
            secondarySlots = model.secondarySlots.map {
                AdditionalInfoDomainModel(
                    icon = it.icon,
                    text = it.text,
                    onClick = it.onClick?.let { onClick ->
                        { problemCategory ->
                            onClick(
                                problemCategoryPresentationModelToDomainMapper.toPresentation(
                                    problemCategory
                                )
                            )
                        }
                    }
                )
            }
        )
    }

    fun toPresentation(model: ProblemCategoryInfoDomainModel): ProblemCategoryInfoPresentationModel {
        return ProblemCategoryInfoPresentationModel(
            mainSlot = model.mainSlot,
            secondarySlots = model.secondarySlots.map {
                AdditionalInfoPresentationModel<ProblemCategoryPresentationModel>(
                    icon = it.icon,
                    text = it.text,
                    onClick = it.onClick?.let { onClick ->
                        { problemCategory ->
                            onClick(
                                problemCategoryPresentationModelToDomainMapper.toDomain(
                                    problemCategory
                                )
                            )
                        }
                    }
                )
            }
        )
    }

}
