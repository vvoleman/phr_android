package cz.vvoleman.phr.di.event

import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.SpecificMedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.featureEvent.presentation.mapper.core.EventPresentationModelToDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun providesEventPresentationModelToDomainMapper(
        patientMapper: PatientPresentationModelToDomainMapper,
        workerMapper: SpecificMedicalWorkerPresentationModelToDomainMapper
    ): EventPresentationModelToDomainMapper {
        return EventPresentationModelToDomainMapper(patientMapper, workerMapper)
    }

}
