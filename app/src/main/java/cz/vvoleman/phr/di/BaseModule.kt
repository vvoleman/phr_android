package cz.vvoleman.phr.di

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.UseCaseExecutor
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.presentation.viewmodel.coroutine.AppCoroutineContextProvider
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseModule {

    @Provides
    fun providesCoroutineContextProvider(): CoroutineContextProvider =
        AppCoroutineContextProvider()

    @Provides
    fun providesUseCaseExecutorProvider(): UseCaseExecutorProvider =
        ::UseCaseExecutor

    @Provides
    @Singleton
    fun providesNavManager(): NavManager =
        NavManager()

}