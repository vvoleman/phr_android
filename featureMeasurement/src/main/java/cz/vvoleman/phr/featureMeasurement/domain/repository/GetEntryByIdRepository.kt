package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupEntryDomainModel

interface GetEntryByIdRepository {

    suspend fun getEntryById(measurementGroupId: String): List<MeasurementGroupEntryDomainModel>

}
