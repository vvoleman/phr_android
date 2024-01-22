package cz.vvoleman.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupDataSourceModel
import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupWithDetailsDataSourceModel
import cz.vvoleman.featureMeasurement.data.model.addEdit.SaveMeasurementGroupDataModel
import cz.vvoleman.featureMeasurement.data.model.core.MeasurementGroupDataModel
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper

class MeasurementGroupDataSourceModelToDataMapper(
    private val scheduleItemMapper: MeasurementGroupScheduleItemDataSourceModelToDataMapper,
    private val numericFieldMapper: NumericFieldDataSourceModelToDataMapper,
    private val patientMapper: PatientDataSourceModelToDomainMapper,
    private val entryMapper: MeasurementGroupEntryDataSourceModelToDataMapper,
) {

    suspend fun toData(model: MeasurementGroupWithDetailsDataSourceModel): MeasurementGroupDataModel {
        val numericFields = model.numericFields.map { numericFieldMapper.toData(it) }

        return MeasurementGroupDataModel(
            id = model.measurementGroup.id.toString(),
            name = model.measurementGroup.name,
            patient = patientMapper.toDomain(model.patient),
            scheduleItems = model.scheduleItems.map { scheduleItemMapper.toData(it) },
            fields = numericFields,
            entries = entryMapper.toData(model.entries),
        )
    }

    suspend fun toData(models: List<MeasurementGroupWithDetailsDataSourceModel>): List<MeasurementGroupDataModel> {
        return models.map { toData(it) }
    }

    fun toDataSource(model: MeasurementGroupDataModel): MeasurementGroupDataSourceModel {
        return MeasurementGroupDataSourceModel(
            id = model.id.toIntOrNull(),
            name = model.name,
            patientId = model.patient.id.toInt(),
        )
    }

    fun toDataSource(models: List<MeasurementGroupDataModel>): List<MeasurementGroupDataSourceModel> {
        return models.map { toDataSource(it) }
    }

    fun toDataSource(model: SaveMeasurementGroupDataModel): MeasurementGroupDataSourceModel {
        return MeasurementGroupDataSourceModel(
            id = model.id?.toInt(),
            name = model.name,
            patientId = model.patientId.toInt(),
        )
    }

}
