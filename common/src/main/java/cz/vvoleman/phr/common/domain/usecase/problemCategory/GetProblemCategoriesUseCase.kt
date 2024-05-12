package cz.vvoleman.phr.common.domain.usecase.problemCategory

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryInfoDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryWithInfoDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.request.GetProblemCategoriesRequest
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoriesRepository
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoriesAdditionalInfoEvent

class GetProblemCategoriesUseCase(
    private val eventBusChannel: EventBusChannel<
        GetProblemCategoriesAdditionalInfoEvent,
        Map<ProblemCategoryDomainModel, ProblemCategoryInfoDomainModel>
        >,
    private val getProblemCategoriesRepository: GetProblemCategoriesRepository,
    coroutineContextProvider: CoroutineContextProvider
) :
    BackgroundExecutingUseCase<GetProblemCategoriesRequest, List<ProblemCategoryWithInfoDomainModel>>(
        coroutineContextProvider
    ) {

    override suspend fun executeInBackground(
        request: GetProblemCategoriesRequest
    ): List<ProblemCategoryWithInfoDomainModel> {
        val categories = getProblemCategoriesRepository.getProblemCategories(request.patientId)

        val event = GetProblemCategoriesAdditionalInfoEvent(categories)

        val channelResults = eventBusChannel.pushEvent(event)

        return channelResults.map { it.entries }.flatten().groupBy {
            it.key
        }.map { (category, value) ->
            val values = value.sortedBy { it.value.priority }
            val secondaries = values.flatMap { it.value.secondarySlots }
            val primary = values.last().value.mainSlot

            ProblemCategoryWithInfoDomainModel(
                problemCategory = category,
                info = ProblemCategoryInfoDomainModel(
                    mainSlot = primary,
                    secondarySlots = secondaries
                )
            )
        }
    }
}
