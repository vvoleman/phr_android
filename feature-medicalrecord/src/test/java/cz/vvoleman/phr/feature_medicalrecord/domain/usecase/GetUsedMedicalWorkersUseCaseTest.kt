package cz.vvoleman.phr.feature_medicalrecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalWorkerDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedMedicalWorkersRepository
import cz.vvoleman.phr.feature_medicalrecord.test.coroutine.FakeCoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class GetUsedMedicalWorkersUseCaseTest {

    private lateinit var useCase: GetUsedMedicalWorkersUseCase
    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @Mock
    private lateinit var getUsedMedicalWorkersRepository: GetUsedMedicalWorkersRepository

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        useCase = GetUsedMedicalWorkersUseCase(getUsedMedicalWorkersRepository, coroutineContextProvider)
    }


    @Test
    fun `Get Used Medical Workers, valid`() =  runTest {
        val id = "1"
        val willReturn = listOf(
            MedicalWorkerDomainModel(
                id = "1",
                name = "Worker 1",
                patientId = id
            ),
            MedicalWorkerDomainModel(
                id = "2",
                name = "Worker 2",
                patientId = id
            ),
            MedicalWorkerDomainModel(
                id = "3",
                name = "Worker 3",
                patientId = id
            )
        )

        given(getUsedMedicalWorkersRepository.getUsedMedicalWorkers(id)).willReturn(willReturn)

        val actualValue = useCase.executeInBackground(id)

        assertEquals(willReturn.size, actualValue.size)

        for (worker in willReturn) {
            assertEquals(id, actualValue)
        }
    }

    @Test
    fun `Get Used Medical Workers, invalid`() =  runTest {
        val id = "1"
        val willReturn = listOf(
            MedicalWorkerDomainModel(
                id = "1",
                name = "Worker 1",
                patientId = "2"
            ),
        )

        given(getUsedMedicalWorkersRepository.getUsedMedicalWorkers(id)).willReturn(willReturn)

        val actualValue = useCase.executeInBackground(id)

        assertNotEquals(willReturn.first().patientId, actualValue.first().patientId)
    }
}