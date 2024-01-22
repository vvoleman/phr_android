package cz.vvoleman.featureMeasurement.presentation.mapper.core

import cz.vvoleman.featureMeasurement.domain.model.core.MeasurementGroupEntryDomainModel
import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupEntryPresentationModel

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
