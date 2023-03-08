package cz.vvoleman.phr.feature_medicalrecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.AddEditMedicalRecordRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AddEditMedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.AddEditMedicalRecordUseCase
import cz.vvoleman.phr.feature_medicalrecord.test.coroutine.FakeCoroutineContextProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate

@RunWith(MockitoJUnitRunner::class)
class AddEditMedicalRecordUseCaseTest {

    private lateinit var classUnderTest: AddEditMedicalRecordUseCase

    @Mock
    private lateinit var addEditMedicalRecordRepository: AddEditMedicalRecordRepository

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @Before
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        classUnderTest =
            AddEditMedicalRecordUseCase(addEditMedicalRecordRepository, coroutineContextProvider)
    }

    @Test
    fun `Create new medical record`() {
        // Given
        val request = AddEditMedicalRecordDomainModel(
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