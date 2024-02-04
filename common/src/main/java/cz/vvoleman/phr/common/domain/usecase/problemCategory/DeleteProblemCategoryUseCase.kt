package cz.vvoleman.phr.common.domain.usecase.problemCategory

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.presentation.event.problemCategory.DeleteProblemCategoryEvent
import cz.vvoleman.phr.common.domain.model.problemCategory.request.DataDeleteType
import cz.vvoleman.phr.common.domain.model.problemCategory.request.DeleteProblemCategoryRequest
import cz.vvoleman.phr.common.domain.repository.problemCategory.DeleteProblemCategoryRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetDefaultProblemCategoryRepository

class DeleteProblemCategoryUseCase(
    private val eventBusChannel: EventBusChannel<DeleteProblemCategoryEvent, Unit>,
    private val getDefaultProblemCategoryRepository: GetDefaultProblemCategoryRepository,
    private val deleteProblemCategoryRepository: DeleteProblemCategoryRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<DeleteProblemCategoryRequest, Boolean>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: DeleteProblemCategoryRequest): Boolean {
        if (request.problemCategory.isDefault) {
            return false
        }

        val event = getDeleteEvent(request)

        eventBusChannel.pushEvent(event)

        return deleteProblemCategoryRepository.deleteProblemCategory(request.problemCategory.id)
    }

    private suspend fun getDeleteEvent(request: DeleteProblemCategoryRequest): DeleteProblemCategoryEvent {
        val dataDeleteType = when (request.dataDeleteType) {
            is DataDeleteType.MoveToAnother -> {
                if (request.dataDeleteType.backupProblemCategory == null) {
                    val defaultProblemCategory =
                        getDefaultProblemCategoryRepository.getDefaultProblemCategory(request.problemCategory.patientId)
                    DataDeleteType.MoveToAnother(defaultProblemCategory)
                } else {
                    request.dataDeleteType
                }
            }

            is DataDeleteType.DeleteData -> {
                request.dataDeleteType
            }
        }

        return DeleteProblemCategoryEvent(
            problemCategory = request.problemCategory,
            deleteType = dataDeleteType
        )
    }
}
