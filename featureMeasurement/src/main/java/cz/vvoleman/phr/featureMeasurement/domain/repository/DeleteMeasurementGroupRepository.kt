package cz.vvoleman.phr.featureMeasurement.domain.repository

interface DeleteMeasurementGroupRepository {

    suspend fun deleteMeasurementGroup(measurementGroupId: String)

}
