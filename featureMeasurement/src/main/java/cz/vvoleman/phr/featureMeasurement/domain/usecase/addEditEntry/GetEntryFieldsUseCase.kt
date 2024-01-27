package cz.vvoleman.phr.featureMeasurement.domain.usecase.addEditEntry

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry.EntryFieldDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry.GetEntryFieldsRequest
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetEntryByIdRepository

class GetEntryFieldsUseCase(
    private val getEntryByIdRepository: GetEntryByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<GetEntryFieldsRequest, List<EntryFieldDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: GetEntryFieldsRequest): List<EntryFieldDomainModel> {
        val existingEntry = request.entryId?.let { getEntryByIdRepository.getEntryById(it) }
        val values = existingEntry?.values ?: emptyMap()
        val fields = request.measurementGroup.fields
        val entryFields = mutableListOf<EntryFieldDomainModel>()

        fields.forEach { field ->
            entryFields.add(
                EntryFieldDomainModel(
                    fieldId = field.id,
                    name = field.name,
                    value = values[field.id]
                )
            )
        }

        return entryFields
    }
}
