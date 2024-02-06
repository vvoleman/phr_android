package cz.vvoleman.phr.di.event

import cz.vvoleman.phr.featureEvent.data.mapper.core.EventDataModelToDomainMapper
import cz.vvoleman.phr.featureEvent.data.mapper.core.LongToReminderOffsetMapper
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

}
