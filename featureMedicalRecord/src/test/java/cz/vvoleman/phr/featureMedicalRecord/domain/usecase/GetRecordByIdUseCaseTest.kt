package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetRecordByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.test.coroutine.FakeCoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class GetRecordByIdUseCaseTest {

    private lateinit var useCase: GetRecordByIdUseCase

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @Mock
    private lateinit var getRecordByIdRepository: GetRecordByIdRepository

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        useCase = GetRecordByIdUseCase(getRecordByIdRepository, coroutineContextProvider)
    }

    @Test
    fun `Get Record By Id, valid`() = runTest {
        val id = "1"
        val willReturn = getRecord(id)

        given(getRecordByIdRepository.getRecordById(id)).willReturn(willReturn)

        val actualValue = useCase.executeInBackground(id)

        assertEquals(willReturn, actualValue)
    }

    @Test
    fun `Get Record By Id, invalid`() = runTest {
        val id = "1"

        given(getRecordByIdRepository.getRecordById(id)).willReturn(null)

        val actualValue = useCase.executeInBackground(id)

        assertNull(actualValue)
    }

    private fun getRecord(id: String): MedicalRecordDomainModel {
        return MedicalRecordDomainModel(
            id = id,
            createdAt = LocalDate.now(),
            visitDate = LocalDate.now(),
            patient = PatientDomainModel(
                id = "1",
                name = "Name",
                birthDate = LocalDate.of(2001, 1, 1)
            )
        )
    }
}
