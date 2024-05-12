package cz.vvoleman.phr.featureMeasurement.data.mapper.addEdit

import cz.vvoleman.phr.featureMeasurement.data.mapper.core.MeasurementGroupFieldDataToDomainMapper
import cz.vvoleman.phr.featureMeasurement.data.model.addEdit.SaveMeasurementGroupDataModel
import cz.vvoleman.phr.featureMeasurement.domain.model.addEdit.SaveMeasurementGroupDomainModel

class SaveMeasurementGroupDataModelToDomainMapper(
    private val fieldMapper: MeasurementGroupFieldDataToDomainMapper,
    private val saveScheduleItemMapper: SaveMeasurementGroupScheduleItemDataModelToDomainMapper,
) {

    fun toData(model: SaveMeasurementGroupDomainModel): SaveMeasurementGroupDataModel {
        return SaveMeasurementGroupDataModel(
            id = model.id,
            name = model.name,
            patientId = model.patientId,
            problemCategoryId = model.problemCategoryId,
            scheduleItems = saveScheduleItemMapper.toData(model.scheduleItems),
            fields = fieldMapper.toData(model.fields),
        )
    }
}
