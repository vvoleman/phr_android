package cz.vvoleman.phr.featureMeasurement.domain.model.list

data class GroupMeasurementGroupRequest(
    val patientId: String,
    val orderByDirection: OrderByDirection,
) {
    enum class OrderByDirection { ASC, DESC }
}
