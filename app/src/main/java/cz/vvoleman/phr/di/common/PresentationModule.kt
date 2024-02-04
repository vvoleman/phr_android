package cz.vvoleman.phr.di.common

import android.content.Context
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.repository.*
import cz.vvoleman.phr.common.domain.repository.patient.DeletePatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetAllPatientsRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByIdRepository
import cz.vvoleman.phr.common.domain.repository.patient.GetSelectedPatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.SavePatientRepository
import cz.vvoleman.phr.common.domain.repository.patient.SwitchSelectedPatientRepository
import cz.vvoleman.phr.common.domain.usecase.patient.DeletePatientUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetAllPatientsUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.SwitchSelectedPatientUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.addedit.GetPatientByIdUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.addedit.SavePatientUseCase
import cz.vvoleman.phr.common.presentation.eventBus.CommonEventBus
import cz.vvoleman.phr.common.presentation.factory.ColorFactory
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.AddEditMedicalServiceItemPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalFacilityAdditionInfoPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalFacilityPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalServicePresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalServiceWithWorkersPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalWorkerAdditionalInfoPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalWorkerWithInfoPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.SpecificMedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryInfoPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryWithInfoPresentationModelToDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun providesGetSelectedPatientUseCase(
        getSelectedPatientRepository: GetSelectedPatientRepository
    ) = GetSelectedPatientUseCase(
        getSelectedPatientRepository
    )

    @Provides
    fun providesGetAllPatientsUseCase(
        getAllPatientsRepository: GetAllPatientsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetAllPatientsUseCase(
        getAllPatientsRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesSwitchSelectedPatientUseCase(
        switchSelectedPatientRepository: SwitchSelectedPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SwitchSelectedPatientUseCase(
        switchSelectedPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesDeletePatientUseCase(
        deletePatientRepository: DeletePatientRepository,
        getSelectedPatientRepository: GetSelectedPatientRepository,
        getAllPatientsRepository: GetAllPatientsRepository,
        switchSelectedPatientRepository: SwitchSelectedPatientRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = DeletePatientUseCase(
        deletePatientRepository,
        getSelectedPatientRepository,
        getAllPatientsRepository,
        switchSelectedPatientRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesGetPatientByIdUseCase(
        getPatientByIdRepository: GetPatientByIdRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetPatientByIdUseCase(
        getPatientByIdRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesSavePatientUseCase(
        savePatientRepository: SavePatientRepository,
        getPatientByIdRepository: GetPatientByIdRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SavePatientUseCase(
        savePatientRepository,
        getPatientByIdRepository,
        coroutineContextProvider
    )

    @Provides
    fun providesPatientPresentationModelToDomainMapper() = PatientPresentationModelToDomainMapper()

    @Provides
    fun providesMedicalWorkerPresentationModelToDomainMapper() = MedicalWorkerPresentationModelToDomainMapper()

    @Provides
    fun providesMedicalServicePresentationModelToDomainMapper() = MedicalServicePresentationModelToDomainMapper()

    @Provides
    fun providesMedicalWorkerWithInfoPresentationModelToDomainMapper(
        workerMapper: MedicalWorkerPresentationModelToDomainMapper
    ) = MedicalWorkerWithInfoPresentationModelToDomainMapper(workerMapper)

    @Provides
    fun providesMedicalServiceWithWorkersPresentationModelToDomainMapper(
        serviceMapper: MedicalServicePresentationModelToDomainMapper,
        workerInfoMapper: MedicalWorkerWithInfoPresentationModelToDomainMapper
    ) = MedicalServiceWithWorkersPresentationModelToDomainMapper(serviceMapper, workerInfoMapper)

    @Provides
    fun providesMedicalFacilityPresentationModelToDomainMapper(
        serviceWithWorkersMapper: MedicalServiceWithWorkersPresentationModelToDomainMapper
    ) = MedicalFacilityPresentationModelToDomainMapper(serviceWithWorkersMapper)

    @Provides
    fun providesAddEditMedicalServiceItemPresentationModelToDomainMapper(
        facilityMapper: MedicalFacilityPresentationModelToDomainMapper
    ) =
        AddEditMedicalServiceItemPresentationModelToDomainMapper(facilityMapper)

    @Provides
    fun providesMedicalWorkerAdditionalInfoPresentationModelToDomainMapper(
        workerMapper: MedicalWorkerPresentationModelToDomainMapper
    ) = MedicalWorkerAdditionalInfoPresentationModelToDomainMapper(workerMapper)

    @Provides
    fun providesMedicalFacilityAdditionInfoPresentationModelToDomainMapper(
        facilityMapper: MedicalFacilityPresentationModelToDomainMapper
    ) = MedicalFacilityAdditionInfoPresentationModelToDomainMapper(facilityMapper)

    @Provides
    fun providesProblemCategoryPresentationModelToDomainMapper() = ProblemCategoryPresentationModelToDomainMapper()

    @Provides
    fun providesProblemCategoryInfoPresentationModelToDomainMapper(
        problemCategoryPresentationModelToDomainMapper: ProblemCategoryPresentationModelToDomainMapper
    ) = ProblemCategoryInfoPresentationModelToDomainMapper(problemCategoryPresentationModelToDomainMapper)

    @Provides
    fun providesProblemCategoryWithInfoPresentationModelToDomainMapper(
        problemCategoryPresentationModelToDomainMapper: ProblemCategoryPresentationModelToDomainMapper,
        problemCategoryInfoPresentationModelToDomainMapper: ProblemCategoryInfoPresentationModelToDomainMapper
    ) = ProblemCategoryWithInfoPresentationModelToDomainMapper(
        problemCategoryPresentationModelToDomainMapper,
        problemCategoryInfoPresentationModelToDomainMapper
    )

    @Provides
    fun providesColorFactory(
        @ApplicationContext context: Context
    ) : ColorFactory = ColorFactory(context)

    @Provides
    fun providesSpecificMedicalWorkerPresentationModelToDomainMapper(
        medicalWorkerMapper: MedicalWorkerPresentationModelToDomainMapper,
        medicalServiceMapper: MedicalServicePresentationModelToDomainMapper
    ) = SpecificMedicalWorkerPresentationModelToDomainMapper(
        medicalWorkerMapper,
        medicalServiceMapper
    )

    @Provides
    fun providesCommonEventBus() = CommonEventBus

    @Provides
    fun providesGetWorkerAdditionalInfoBus(
        eventBus: CommonEventBus
    ) = eventBus.getWorkerAdditionalInfoBus

    @Provides
    fun providesGetFacilityAdditionalInfoBus(
        eventBus: CommonEventBus
    ) = eventBus.getFacilityAdditionalInfoBus

    @Provides
    fun providesGetCategoryAdditionalInfoBus(
        eventBus: CommonEventBus
    ) = eventBus.getCategoryAdditionalInfoBus

    @Provides
    fun providesEventBusChannelMedicalWorkerDeleted(
        eventBus: CommonEventBus
    ) = eventBus.medicalWorkerDeletedEvent

    @Provides
    fun providesDeleteProblemCategoryEventBus(
        eventBus: CommonEventBus
    ) = eventBus.deleteProblemCategoryBus
}
