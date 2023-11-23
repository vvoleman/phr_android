package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.GroupScheduleItemsRequest

@Suppress("MaximumLineLength", "Indentation")
class GroupScheduleItemsUseCase(
    coroutineContextProvider: CoroutineContextProvider
) :
    BackgroundExecutingUseCase<
        GroupScheduleItemsRequest,
        List<GroupedItemsDomainModel<ScheduleItemWithDetailsDomainModel>>
        >(
        coroutineContextProvider
    ) {

    override suspend fun executeInBackground(
        request: GroupScheduleItemsRequest
    ): List<GroupedItemsDomainModel<ScheduleItemWithDetailsDomainModel>> {
        val groups = request.scheduleItems.groupBy {
            val time = it.scheduleItem.getTranslatedDateTime(request.currentDateTime)

            val date = time.toLocalDate().toString()
            val hour = time.toLocalTime().hour

            "$date-$hour"
        }.toSortedMap()

        return groups.map { (key, value) ->
            GroupedItemsDomainModel(
                key,
                value.sortedWith(
                    compareBy(
                        { it.scheduleItem.time },
                        { it.medicine.name }
                    )
                )
            )
        }
    }
}
