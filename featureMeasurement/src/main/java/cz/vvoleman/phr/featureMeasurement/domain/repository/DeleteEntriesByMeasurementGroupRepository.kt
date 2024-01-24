package cz.vvoleman.phr.featureMeasurement.domain.repository

interface DeleteEntriesByMeasurementGroupRepository {

    suspend fun deleteEntriesByMeasurementGroup(measurementGroupId: String)

}
