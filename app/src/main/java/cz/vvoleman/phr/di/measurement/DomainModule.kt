package cz.vvoleman.phr.di.measurement

import cz.vvoleman.phr.featureMeasurement.domain.repository.DeleteMeasurementGroupAlarmRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.SaveMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.ScheduleMeasurementGroupRepository
import cz.vvoleman.phr.featureMeasurement.domain.usecase.addEdit.SaveMeasurementGroupUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.addEdit.ScheduleMeasurementGroupAlertUseCase
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.featureMeasurement.domain.facade.MeasurementTranslateDateTimeFacade
import cz.vvoleman.phr.featureMeasurement.domain.facade.NextMeasurementGroupScheduleFacade
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupsByPatientRepository
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.GetNextScheduledMeasurementGroupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesSaveMeasurementGroupUseCase(
        saveMeasurementGroupRepository: SaveMeasurementGroupRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveMeasurementGroupUseCase(saveMeasurementGroupRepository, coroutineContextProvider)

    @Provides
    fun scheduleScheduleMeasurementGroupAlertUseCase(
        scheduleMeasurementGroupRepository: ScheduleMeasurementGroupRepository,
        deleteMeasurementGroupAlarmRepository: DeleteMeasurementGroupAlarmRepository,
        getMeasurementGroupByIdRepository: GetMeasurementGroupRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = ScheduleMeasurementGroupAlertUseCase(
        scheduleMeasurementGroupRepository = scheduleMeasurementGroupRepository,
        deleteMeasurementGroupAlarmRepository = deleteMeasurementGroupAlarmRepository,
        getMeasurementGroupByIdRepository = getMeasurementGroupByIdRepository,
        coroutineContextProvider = coroutineContextProvider
    )

    @Provides
    fun providesMeasurementGroupTranslateDateTimeFacade() = MeasurementTranslateDateTimeFacade()

    @Provides
    fun providesNextMeasurementGroupScheduleFacade(
        translateFacade: MeasurementTranslateDateTimeFacade
    ) = NextMeasurementGroupScheduleFacade(translateFacade)

    @Provides
    fun providesGetNextScheduledMeasurementGroupUseCase(
        getMeasurementGroupsByPatientRepository: GetMeasurementGroupsByPatientRepository,
        translateDateTimeFacade: MeasurementTranslateDateTimeFacade,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetNextScheduledMeasurementGroupUseCase(
        getMeasurementGroupsByPatientRepository = getMeasurementGroupsByPatientRepository,
        translateDateTimeFacade = translateDateTimeFacade,
        coroutineContextProvider = coroutineContextProvider
    )

}
