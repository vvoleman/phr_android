package cz.vvoleman.phr.featureMeasurement.presentation.mapper.core

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupEntryDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupEntryPresentationModel

class MeasurementGroupEntryPresentationModelToDomainMapper {

    fun toDomain(model: MeasurementGroupEntryPresentationModel): MeasurementGroupEntryDomainModel {
        return MeasurementGroupEntryDomainModel(
            id = model.id,
            createdAt = model.createdAt,
            values = model.values,
            scheduleItemId = model.scheduleItemId,
        )
    }

    fun toPresentation(model: MeasurementGroupEntryDomainModel): MeasurementGroupEntryPresentationModel {
        return MeasurementGroupEntryPresentationModel(
            id = model.id,
            createdAt = model.createdAt,
            values = model.values,
            scheduleItemId = model.scheduleItemId,
        )
    }
}
