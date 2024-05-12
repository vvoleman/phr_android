package cz.vvoleman.phr.featureMeasurement.domain.usecase.list

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.common.domain.model.enum.SortDirection
import cz.vvoleman.phr.featureMeasurement.domain.model.list.GroupScheduledMeasurementsRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.list.ScheduledMeasurementGroupDomainModel

class GroupScheduledMeasurementsByTimeUseCase(
    coroutineContextProvider: CoroutineContextProvider
) :
    BackgroundExecutingUseCase<
        GroupScheduledMeasurementsRequest,
        List<GroupedItemsDomainModel<ScheduledMeasurementGroupDomainModel>>
        >(
        coroutineContextProvider
    ) {

    override suspend fun executeInBackground(
        request: GroupScheduledMeasurementsRequest
    ): List<GroupedItemsDomainModel<ScheduledMeasurementGroupDomainModel>> {
        val groups = request.scheduleItems.groupBy {
            val date = it.dateTime.toLocalDate().toString()
            val hour = it.dateTime.toLocalTime().hour
            "$date-$hour"
        }.toSortedMap(
            if (request.sortDirection == SortDirection.ASC) {
                compareBy { it }
            } else {
                compareByDescending { it }
            }
        )

        return groups.map { (key, value) ->
            GroupedItemsDomainModel(
                key,
                value.sortedWith(
                    compareBy(
                        { it.measurementGroup.id },
                        { it.measurementGroup.name }
                    )
                )
            )
        }
    }
}
