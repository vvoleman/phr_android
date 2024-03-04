package cz.vvoleman.phr.featureMeasurement.data.datasource.room.mapper

import cz.vvoleman.phr.common.data.mapper.PatientDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.problemCategory.ProblemCategoryDataSourceModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.MeasurementGroupWithDetailsDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.model.addEdit.SaveMeasurementGroupDataModel
import cz.vvoleman.phr.featureMeasurement.data.model.core.MeasurementGroupDataModel

class MeasurementGroupDataSourceModelToDataMapper(
    private val scheduleItemMapper: MeasurementGroupScheduleItemDataSourceModelToDataMapper,
    private val numericFieldMapper: NumericFieldDataSourceModelToDataMapper,
    private val patientMapper: PatientDataSourceModelToDomainMapper,
    private val problemCategoryMapper: ProblemCategoryDataSourceModelToDomainMapper,
    private val entryMapper: MeasurementGroupEntryDataSourceModelToDataMapper,
) {

    suspend fun toData(model: MeasurementGroupWithDetailsDataSourceModel): MeasurementGroupDataModel {
        val numericFields = model.numericFields.map { numericFieldMapper.toData(it) }

        return MeasurementGroupDataModel(
            id = model.measurementGroup.id.toString(),
            name = model.measurementGroup.name,
            patient = patientMapper.toDomain(model.patient),
            problemCategory = model.problemCategory?.let { problemCategoryMapper.toDomain(it) },
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
            problemCategoryId = model.problemCategory?.id?.toIntOrNull(),
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
            problemCategoryId = model.problemCategoryId?.toIntOrNull(),
        )
    }

}
