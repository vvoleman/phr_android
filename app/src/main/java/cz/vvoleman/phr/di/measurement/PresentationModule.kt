package cz.vvoleman.phr.di.measurement

import cz.vvoleman.phr.common.presentation.eventBus.CommonEventBus
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupsByPatientRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupsByProblemCategoryRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.UpdateMeasurementGroupProblemCategoryRepository
import cz.vvoleman.phr.featureMeasurement.domain.usecase.detail.GetFieldStatsUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.DeleteMeasurementGroupUseCase
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.addEditEntry.EntryFieldPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupEntryPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupFieldPresentationToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.NumericFieldPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.ScheduledMeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.UnitGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.UnitPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.detail.FieldStatsPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.list.MeasurementGroupPresentationModelToNextScheduleMapper
import cz.vvoleman.phr.featureMeasurement.presentation.subscriber.MeasurementListener
import cz.vvoleman.phr.featureMeasurement.presentation.subscriber.ProblemCategoryDetailProvider
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.EntryInfoUiModelToMeasurementGroupMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun provideNumericFieldPresentationModelToDomainMapper(
        unitGroupMapper: UnitGroupPresentationModelToDomainMapper,
    ): NumericFieldPresentationModelToDomainMapper {
        return NumericFieldPresentationModelToDomainMapper(
            unitGroupMapper = unitGroupMapper,
        )
    }

    @Provides
    fun provideUnitGroupPresentationModelToDomainMapper(
        unitMapper: UnitPresentationModelToDomainMapper,
    ): UnitGroupPresentationModelToDomainMapper {
        return UnitGroupPresentationModelToDomainMapper(
            unitMapper = unitMapper,
        )
    }

    @Provides
    fun provideUnitPresentationModelToDomainMapper(): UnitPresentationModelToDomainMapper {
        return UnitPresentationModelToDomainMapper()
    }

    @Provides
    fun provideMeasurementGroupScheduleItemPresentationModelToDomainMapper(): MeasurementGroupScheduleItemPresentationModelToDomainMapper {
        return MeasurementGroupScheduleItemPresentationModelToDomainMapper()
    }

    @Provides
    fun provideMeasurementGroupEntryPresentationModelToDomainMapper(): MeasurementGroupEntryPresentationModelToDomainMapper {
        return MeasurementGroupEntryPresentationModelToDomainMapper()
    }

    @Provides
    fun provideMeasurementGroupPresentationModelToDomainMapper(
        scheduleItemMapper: MeasurementGroupScheduleItemPresentationModelToDomainMapper,
        entryMapper: MeasurementGroupEntryPresentationModelToDomainMapper,
        fieldMapper: MeasurementGroupFieldPresentationToDomainMapper,
        patientMapper: PatientPresentationModelToDomainMapper,
        problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper,
    ): MeasurementGroupPresentationModelToDomainMapper {
        return MeasurementGroupPresentationModelToDomainMapper(
            scheduleItemMapper = scheduleItemMapper,
            entryMapper = entryMapper,
            fieldMapper = fieldMapper,
            patientMapper = patientMapper,
            problemCategoryMapper = problemCategoryMapper,
        )
    }

    @Provides
    fun providesMeasurementGroupFieldPresentationToDomainMapper(
        numericMapper: NumericFieldPresentationModelToDomainMapper,
    ): MeasurementGroupFieldPresentationToDomainMapper {
        return MeasurementGroupFieldPresentationToDomainMapper(
            numericMapper = numericMapper,
        )
    }

    @Provides
    fun providesScheduledMeasurementGroupPresentationModelToDomainMapper(
        measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper
    ): ScheduledMeasurementGroupPresentationModelToDomainMapper {
        return ScheduledMeasurementGroupPresentationModelToDomainMapper(
            measurementGroupMapper = measurementGroupMapper
        )
    }

    @Provides
    fun providesMeasurementGroupPresentationModelToNextScheduleMapper() =
        MeasurementGroupPresentationModelToNextScheduleMapper()

    @Provides
    fun providesEntryFieldPresentationModelToDomainMapper() =
        EntryFieldPresentationModelToDomainMapper()

    @Provides
    fun providesFieldStatsPresentationModelToDomainMapper() =
        FieldStatsPresentationModelToDomainMapper()

    @Provides
    fun providesMeasurementListener(
        commonEventBus: CommonEventBus,
        getMeasurementsByProblemCategoryRepository: GetMeasurementGroupsByProblemCategoryRepository,
        measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
        problemCategoryDetailProvider: ProblemCategoryDetailProvider,
        entryMapper: EntryInfoUiModelToMeasurementGroupMapper,
        fieldStatsUseCase: GetFieldStatsUseCase,
        deleteMeasurementGroupUseCase: DeleteMeasurementGroupUseCase,
        updateMeasurementGroupProblemCategoryRepository: UpdateMeasurementGroupProblemCategoryRepository,
        fieldStatsMapper: FieldStatsPresentationModelToDomainMapper,
        getMeasurementByPatientRepository: GetMeasurementGroupsByPatientRepository,
    ) = MeasurementListener(
        commonEventBus = commonEventBus,
        getMeasurementsByProblemCategoryRepository = getMeasurementsByProblemCategoryRepository,
        measurementGroupMapper = measurementGroupMapper,
        problemCategoryDetailProvider = problemCategoryDetailProvider,
        entryMapper = entryMapper,
        fieldStatsUseCase = fieldStatsUseCase,
        fieldStatsMapper = fieldStatsMapper,
        deleteMeasurementGroupUseCase = deleteMeasurementGroupUseCase,
        updateMeasurementGroupProblemCategoryRepository = updateMeasurementGroupProblemCategoryRepository,
        getMeasurementByPatientRepository = getMeasurementByPatientRepository,
    )
}
