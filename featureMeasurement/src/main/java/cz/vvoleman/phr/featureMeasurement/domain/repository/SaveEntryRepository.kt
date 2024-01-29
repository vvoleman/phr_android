package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry.SaveMeasurementGroupEntryDomainModel

interface SaveEntryRepository {

    suspend fun saveEntry(
        model: SaveMeasurementGroupEntryDomainModel
    ): String
}
