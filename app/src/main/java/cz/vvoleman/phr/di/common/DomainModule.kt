package cz.vvoleman.phr.di.common

import cz.vvoleman.phr.common.domain.mapper.PatientDomainModelToAddEditMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesPatientDomainModelToAddEditMapper() = PatientDomainModelToAddEditMapper()
}
