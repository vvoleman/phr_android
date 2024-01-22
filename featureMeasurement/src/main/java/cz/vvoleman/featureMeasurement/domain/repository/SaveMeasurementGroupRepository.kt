package cz.vvoleman.featureMeasurement.domain.repository

import cz.vvoleman.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupDomainModel
import cz.vvoleman.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

interface SaveMeasurementGroupRepository {

    suspend fun saveMeasurementGroup(model: SaveMeasurementGroupDomainModel): MeasurementGroupDomainModel?

}
