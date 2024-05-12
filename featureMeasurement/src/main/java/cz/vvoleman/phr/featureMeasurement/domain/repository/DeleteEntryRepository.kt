package cz.vvoleman.phr.featureMeasurement.domain.repository

interface DeleteEntryRepository {

    suspend fun deleteEntry(entryId: String)
}
