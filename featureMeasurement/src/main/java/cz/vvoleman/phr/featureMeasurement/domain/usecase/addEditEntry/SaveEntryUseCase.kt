package cz.vvoleman.phr.featureMeasurement.domain.usecase.addEditEntry

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry.SaveEntryRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry.SaveMeasurementGroupEntryDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupEntryDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.repository.SaveEntryRepository
import java.time.ZoneOffset

class SaveEntryUseCase(
    private val saveEntryRepository: SaveEntryRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SaveEntryRequest, Unit>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SaveEntryRequest) {
        val entry = request.entry ?: MeasurementGroupEntryDomainModel(
            id = request.dateTime.toEpochSecond(ZoneOffset.UTC).toString(),
            createdAt = request.dateTime,
            values = emptyMap()
        )

        val updatedEntry = entry.copy(
            values = request.entryFields.associate { it.fieldId to (it.value ?: "") }
        )

        val model = SaveMeasurementGroupEntryDomainModel(
            id = updatedEntry.id,
            measurementGroupId = request.measurementGroup.id,
            values = updatedEntry.values,
            scheduleItemId = updatedEntry.scheduleItemId,
            createdAt = updatedEntry.createdAt
        )
        saveEntryRepository.saveEntry(model)
    }
}
