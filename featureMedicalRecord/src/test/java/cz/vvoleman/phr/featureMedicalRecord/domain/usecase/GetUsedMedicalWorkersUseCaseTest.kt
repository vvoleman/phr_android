package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetUsedMedicalWorkersRepository
import cz.vvoleman.phr.featureMedicalRecord.test.coroutine.FakeCoroutineContextProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
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
    fun `Get Used Medical Workers, valid`() = runTest {
        val id = "1"
        val willReturn = listOf(
            mockWorker(
                workerId = "1",
                specific = "1",
                patient = id
            ),
            mockWorker(
                workerId = "2",
                specific = "2",
                patient = id
            ),
            mockWorker(
                workerId = "2",
                specific = "3",
                patient = id
            ),
        )

        given(getUsedMedicalWorkersRepository.getUsedMedicalWorkers(id)).willReturn(willReturn)

        val actualValue = useCase.executeInBackground(id)

        assertEquals(2, actualValue.size)

        assertEquals(willReturn.first().id, actualValue.first().id)
    }

    private fun mockWorker(workerId: String, specific: String, patient: String): SpecificMedicalWorkerDomainModel {
        val worker = mockk<MedicalWorkerDomainModel>(relaxed = true) {
            every { id } returns workerId
            every { patientId } returns patient
        }
        return mockk(relaxed = true) {
            every { id } returns specific
            every { medicalWorker } returns worker
        }
    }
}
