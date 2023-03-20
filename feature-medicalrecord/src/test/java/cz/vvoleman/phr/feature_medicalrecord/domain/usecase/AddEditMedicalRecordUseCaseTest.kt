package cz.vvoleman.phr.feature_medicalrecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AddEditDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.AddEditMedicalRecordRepository
import cz.vvoleman.phr.feature_medicalrecord.test.coroutine.FakeCoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class AddEditMedicalRecordUseCaseTest {

    private lateinit var classUnderTest: AddEditMedicalRecordUseCase

    @Mock
    private lateinit var addEditMedicalRecordRepository: AddEditMedicalRecordRepository

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        classUnderTest =
            AddEditMedicalRecordUseCase(addEditMedicalRecordRepository, coroutineContextProvider)
    }

    @Test
    fun `Create new medical record`() = runTest {
        // Given
        val request = AddEditDomainModel(
            createdAt = LocalDate.now(),
            patientId = "#1",
        )
        val willReturn = "123"

        given(addEditMedicalRecordRepository.save(request)).willReturn(willReturn)

        // When
        val actualValue = classUnderTest.executeInBackground(request)

        // Then
        assertEquals(willReturn, actualValue)
    }
}