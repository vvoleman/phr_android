package cz.vvoleman.phr.featureMeasurement.domain.usecase.list

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.list.GroupMeasurementGroupRequest
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupsByPatientRepository

class GroupMeasurementGroupUseCase(
    private val getMeasurementGroupsByPatientRepository: GetMeasurementGroupsByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider
) :
    BackgroundExecutingUseCase<GroupMeasurementGroupRequest, List<GroupedItemsDomainModel<MeasurementGroupDomainModel>>>(
        coroutineContextProvider
    ) {

    override suspend fun executeInBackground(
        request: GroupMeasurementGroupRequest
    ): List<GroupedItemsDomainModel<MeasurementGroupDomainModel>> {
        val groups = getMeasurementGroupsByPatientRepository.getMeasurementGroupsByPatient(request.patientId)

        val groupedGroups = groups.groupBy {
            val char = it.name.first().uppercaseChar()

            if (char.isLetter()) {
                char
            } else {
                '-'
            }
        }

        return groupedGroups.map { (key, value) ->
            GroupedItemsDomainModel(
                key.toString(),
                value.sortedBy { it.name.uppercase() }
            )
        }
    }
}
