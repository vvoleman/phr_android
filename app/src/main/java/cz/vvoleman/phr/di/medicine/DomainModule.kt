package cz.vvoleman.phr.di.medicine

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.featureMedicine.domain.repository.ChangeMedicineScheduleAlarmEnabledRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.DeleteMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.DeleteScheduleAlarmRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineByIdRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetMedicineScheduleByIdRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.GetSchedulesByMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SaveMedicineScheduleRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.ScheduleMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.SearchMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import cz.vvoleman.phr.featureMedicine.domain.usecase.DeleteMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetMedicineByIdUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetMedicineScheduleByIdUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetNextScheduledUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GetScheduledInTimeRangeUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GroupMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.GroupScheduleItemsUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SaveMedicineScheduleUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.ScheduleMedicineAlertUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.SearchMedicineUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.ToggleScheduleAlarmUseCase
import cz.vvoleman.phr.featureMedicine.domain.usecase.export.GetDataForExportUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesSaveMedicineScheduleUseCase(
        medicineScheduleRepository: SaveMedicineScheduleRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveMedicineScheduleUseCase(
        medicineScheduleRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetMedicineScheduleByIdUseCase(
        medicineRepository: GetMedicineScheduleByIdRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetMedicineScheduleByIdUseCase(
        medicineRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetMedicineByIdUseCase(
        getMedicineByIdRepository: GetMedicineByIdRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetMedicineByIdUseCase(
        getMedicineByIdRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesScheduleMedicineAlertUseCase(
        scheduleMedicineRepository: ScheduleMedicineRepository,
        getMedicineScheduleByIdRepository: GetMedicineScheduleByIdRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = ScheduleMedicineAlertUseCase(
        scheduleMedicineRepository,
        getMedicineScheduleByIdRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetNextScheduledUseCase(
        getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
        coroutineContextProvider: CoroutineContextProvider,
    ) = GetNextScheduledUseCase(
        getSchedulesByPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetScheduledInTimeRangeUseCase(
        getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
        coroutineContextProvider: CoroutineContextProvider,
    ) = GetScheduledInTimeRangeUseCase(
        getSchedulesByPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGroupMedicineSchedules(
        getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
        coroutineContextProvider: CoroutineContextProvider,
    ) = GroupMedicineScheduleUseCase(
        getSchedulesByPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGroupScheduleItemsUseCase(
        coroutineContextProvider: CoroutineContextProvider,
    ) = GroupScheduleItemsUseCase(
        coroutineContextProvider
    )

    @Provides
    fun providesSearchMedicineUseCase(
        searchMedicineRepository: SearchMedicineRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SearchMedicineUseCase(
        searchMedicineRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesDeleteMedicineScheduleUseCase(
        getMedicineScheduleByIdRepository: GetMedicineScheduleByIdRepository,
        deleteMedicineScheduleRepository: DeleteMedicineScheduleRepository,
        deleteScheduleAlarmRepository: DeleteScheduleAlarmRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = DeleteMedicineScheduleUseCase(
        getMedicineScheduleByIdRepository,
        deleteMedicineScheduleRepository,
        deleteScheduleAlarmRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetDataForExportUseCase(
        getSchedulesByMedicineRepository: GetSchedulesByMedicineRepository,
        getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetDataForExportUseCase(
        getSchedulesByMedicineRepository,
        getSchedulesByPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesToggleScheduleAlarmUseCase(
        changeRepository: ChangeMedicineScheduleAlarmEnabledRepository,
        getMedicineScheduleByIdRepository: GetMedicineScheduleByIdRepository,
        scheduleMedicineRepository: ScheduleMedicineRepository,
        deleteScheduleAlarmRepository: DeleteScheduleAlarmRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = ToggleScheduleAlarmUseCase(
        changeRepository,
        getMedicineScheduleByIdRepository,
        scheduleMedicineRepository,
        deleteScheduleAlarmRepository,
        coroutineContextProvider
    )
}
