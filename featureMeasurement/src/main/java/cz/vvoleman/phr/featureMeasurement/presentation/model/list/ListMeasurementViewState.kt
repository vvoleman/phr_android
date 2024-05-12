package cz.vvoleman.phr.featureMeasurement.presentation.model.list

import cz.vvoleman.phr.common.presentation.model.grouped.GroupedItemsPresentationModel
import cz.vvoleman.phr.common.presentation.model.nextSchedule.NextSchedulePresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMeasurement.domain.model.list.GroupMeasurementGroupRequest.OrderByDirection
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.ScheduledMeasurementGroupPresentationModel

data class ListMeasurementViewState(
    val patient: PatientPresentationModel,
    val nextSchedules: List<NextSchedulePresentationModel> = emptyList(),
    val selectedNextSchedule: NextSchedulePresentationModel? = null,
    val groupedMeasurementGroups: List<GroupedItemsPresentationModel<MeasurementGroupPresentationModel>> = emptyList(),
    val groupDirection: OrderByDirection = OrderByDirection.ASC,
    val timelineSchedules: List<GroupedItemsPresentationModel<ScheduledMeasurementGroupPresentationModel>> =
        emptyList(),
)
