package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel

interface SaveMeasurementGroupRepository {

    suspend fun saveMeasurementGroup(model: SaveMeasurementGroupDomainModel): MeasurementGroupDomainModel?
}
