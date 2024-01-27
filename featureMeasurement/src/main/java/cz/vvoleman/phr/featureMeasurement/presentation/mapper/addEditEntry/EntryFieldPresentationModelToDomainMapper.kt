package cz.vvoleman.phr.featureMeasurement.presentation.mapper.addEditEntry

import cz.vvoleman.phr.featureMeasurement.domain.model.addEditEntry.EntryFieldDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.EntryFieldPresentationModel

class EntryFieldPresentationModelToDomainMapper {

    fun toDomain(model: EntryFieldPresentationModel): EntryFieldDomainModel {
        return EntryFieldDomainModel(
            fieldId = model.fieldId,
            name = model.name,
            value = model.value,
        )
    }

    fun toDomain(models: List<EntryFieldPresentationModel>): List<EntryFieldDomainModel> {
        return models.map { toDomain(it) }
    }

    fun toPresentation(model: EntryFieldDomainModel): EntryFieldPresentationModel {
        return EntryFieldPresentationModel(
            fieldId = model.fieldId,
            name = model.name,
            value = model.value,
        )
    }

    fun toPresentation(models: List<EntryFieldDomainModel>): List<EntryFieldPresentationModel> {
        return models.map { toPresentation(it) }
    }

}
