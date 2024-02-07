package cz.vvoleman.phr.di.event

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventAlarmRepository
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventRepository
import cz.vvoleman.phr.featureEvent.domain.repository.GetEventByIdRepository
import cz.vvoleman.phr.featureEvent.domain.repository.SaveEventRepository
import cz.vvoleman.phr.featureEvent.domain.repository.ScheduleEventAlarmRepository
import cz.vvoleman.phr.featureEvent.domain.usecase.addEdit.SaveEventUseCase
import cz.vvoleman.phr.featureEvent.domain.usecase.list.DeleteEventUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesSaveEventUseCase(
        saveEventRepository: SaveEventRepository,
        getEventByIdRepository: GetEventByIdRepository,
        deleteEventAlarmRepository: DeleteEventAlarmRepository,
        scheduleEventAlarmRepository: ScheduleEventAlarmRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveEventUseCase(
        saveEventRepository = saveEventRepository,
        getEventByIdRepository = getEventByIdRepository,
        deleteEventAlarmRepository = deleteEventAlarmRepository,
        scheduleEventAlarmRepository = scheduleEventAlarmRepository,
        coroutineContextProvider = coroutineContextProvider
    )

    @Provides
    fun providesDeleteEventUseCase(
        deleteEventRepository: DeleteEventRepository,
        deleteEventAlarmRepository: DeleteEventAlarmRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = DeleteEventUseCase(
        deleteEventRepository = deleteEventRepository,
        deleteEventAlarmRepository = deleteEventAlarmRepository,
        coroutineContextProvider = coroutineContextProvider
    )

}
