package cz.vvoleman.phr.di.event

import cz.vvoleman.phr.common.data.alarm.AlarmScheduler
import cz.vvoleman.phr.featureEvent.data.alarm.EventAlarmManager
import cz.vvoleman.phr.featureEvent.data.datasource.room.EventDao
import cz.vvoleman.phr.featureEvent.data.datasource.room.mapper.EventDataSourceModelToDataMapper
import cz.vvoleman.phr.featureEvent.data.datasource.room.mapper.EventDataSourceModelToSaveDomainMapper
import cz.vvoleman.phr.featureEvent.data.mapper.core.EventDataModelToDomainMapper
import cz.vvoleman.phr.featureEvent.data.repository.AlarmRepository
import cz.vvoleman.phr.featureEvent.data.repository.EventRepository
import cz.vvoleman.phr.featureEvent.domain.mapper.core.LongToReminderOffsetMapper
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventAlarmRepository
import cz.vvoleman.phr.featureEvent.domain.repository.DeleteEventRepository
import cz.vvoleman.phr.featureEvent.domain.repository.GetEventByIdRepository
import cz.vvoleman.phr.featureEvent.domain.repository.GetEventsByPatientRepository
import cz.vvoleman.phr.featureEvent.domain.repository.SaveEventRepository
import cz.vvoleman.phr.featureEvent.domain.repository.ScheduleEventAlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesLongToReminderOffsetMapper(): LongToReminderOffsetMapper {
        return LongToReminderOffsetMapper()
    }

    @Provides
    fun providesEventDataModelToDomainMapper(
        longToReminderOffsetMapper: LongToReminderOffsetMapper
    ): EventDataModelToDomainMapper {
        return EventDataModelToDomainMapper(longToReminderOffsetMapper)
    }

    @Provides
    fun providesEventRepository(
        eventDao: EventDao,
        saveDomainMapper: EventDataSourceModelToSaveDomainMapper,
        eventMapper: EventDataModelToDomainMapper,
        eventDataSourceMapper: EventDataSourceModelToDataMapper
    ): EventRepository {
        return EventRepository(eventDao, saveDomainMapper, eventMapper, eventDataSourceMapper)
    }

    @Provides
    fun providesGetEventByIdRepository(
        eventRepository: EventRepository
    ): GetEventByIdRepository = eventRepository

    @Provides
    fun providesGetEventsByPatientRepository(
        eventRepository: EventRepository
    ): GetEventsByPatientRepository = eventRepository

    @Provides
    fun providesSaveEventRepository(
        eventRepository: EventRepository
    ): SaveEventRepository = eventRepository

    @Provides
    fun providesAlarmRepository(
        eventDao: EventDao,
        eventMapper: EventDataModelToDomainMapper,
        eventDataSourceMapper: EventDataSourceModelToDataMapper,
        alarmScheduler: AlarmScheduler
    ) = AlarmRepository(
        eventDao = eventDao,
        eventMapper = eventMapper,
        eventDataSourceMapper = eventDataSourceMapper,
        alarmScheduler = alarmScheduler,
    )

    @Provides
    fun providesScheduleEventAlarmRepository(
        alarmRepository: AlarmRepository
    ): ScheduleEventAlarmRepository = alarmRepository

    @Provides
    fun providesDeleteEventAlarmRepository(
        alarmRepository: AlarmRepository
    ): DeleteEventAlarmRepository = alarmRepository

    @Provides
    fun providesEventAlarmManager(
        alarmRepository: AlarmRepository
    ): EventAlarmManager = EventAlarmManager(alarmRepository)

    @Provides
    fun providesDeleteEventRepository(
        eventRepository: EventRepository
    ): DeleteEventRepository = eventRepository
}
