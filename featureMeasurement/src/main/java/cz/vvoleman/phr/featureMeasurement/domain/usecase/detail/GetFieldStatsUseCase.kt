package cz.vvoleman.phr.featureMeasurement.domain.usecase.detail

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.NumericFieldDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.detail.FieldStatsDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.detail.GetFieldStatsRequest
import java.time.LocalDateTime

class GetFieldStatsUseCase(
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<GetFieldStatsRequest, List<FieldStatsDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: GetFieldStatsRequest): List<FieldStatsDomainModel> {
        val numericFields = request.measurementGroup.fields.filterIsInstance<NumericFieldDomainModel>()
        val fieldIds = numericFields.map { it.id }

        val valuesByField = mutableMapOf<String, Map<LocalDateTime, Float>>()

        for (entry in request.measurementGroup.entries) {
            val values = entry.values.filterKeys { fieldIds.contains(it) }

            for ((fieldId, value) in values) {
                val fieldValues = valuesByField.getOrPut(fieldId) { emptyMap() }.toMutableMap()
                fieldValues[entry.createdAt] = value.toFloat()
                valuesByField[fieldId] = fieldValues
            }
        }

        val lastWeek = LocalDateTime.now().minusWeeks(1)
        val stats = mutableListOf<FieldStatsDomainModel>()
        for ((fieldId, value) in valuesByField) {
            val minimalValue = value.values.minOrNull()
            val maximalValue = value.values.maxOrNull()
            val weekAvgValue = value.entries
                .filter { it.key.isAfter(lastWeek) }
                .map { it.value }
                .average().toFloat()

            stats.add(
                FieldStatsDomainModel(
                    fieldId = fieldId,
                    name = numericFields.first { it.id == fieldId }.name,
                    unit = numericFields.first { it.id == fieldId }.unitGroup.defaultUnit.code,
                    values = value,
                    minValue = minimalValue,
                    maxValue = maximalValue,
                    weekAvgValue = weekAvgValue,
                )
            )
        }

        return stats
    }
}
