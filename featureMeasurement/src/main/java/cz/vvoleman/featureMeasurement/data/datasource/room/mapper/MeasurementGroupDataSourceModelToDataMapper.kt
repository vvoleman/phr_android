package cz.vvoleman.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupDataSourceModel
import cz.vvoleman.featureMeasurement.data.datasource.room.MeasurementGroupWithDetailsDataSourceModel
import cz.vvoleman.featureMeasurement.data.model.MeasurementGroupDataModel
import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper

class MeasurementGroupDataSourceModelToDataMapper(
    private val numericFieldMapper: NumericFieldDataSourceModelToDataMapper,
    private val patientMapper: PatientDataSourceModelToDomainMapper,
) {

    suspend fun toData(model: MeasurementGroupWithDetailsDataSourceModel): MeasurementGroupDataModel {
        val numericFields = model.numericFields.map { numericFieldMapper.toData(it) }

        return MeasurementGroupDataModel(
            id = model.measurementGroup.id.toString(),
            name = model.measurementGroup.name,
            patient = patientMapper.toDomain(model.patient),
            scheduleItems = model.scheduleItems.map { MeasurementGroupScheduleItemDataSourceModelToDataMapper().toData(it) },
            fields = numericFields,
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

}
