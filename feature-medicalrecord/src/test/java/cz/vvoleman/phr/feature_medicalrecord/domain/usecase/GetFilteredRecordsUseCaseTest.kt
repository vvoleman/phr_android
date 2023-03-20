package cz.vvoleman.phr.feature_medicalrecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.GroupedItemsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalWorkerDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.FilterRequestDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.GroupByDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.list.SortByDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetSelectedPatientRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.MedicalRecordFilterRepository
import cz.vvoleman.phr.feature_medicalrecord.test.coroutine.FakeCoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetFilteredRecordsUseCaseTest {

    private lateinit var useCase: GetFilteredRecordsUseCase

    @Mock
    private lateinit var medicalRecordFilterRepository: MedicalRecordFilterRepository

    @Mock
    private lateinit var getSelectedPatientRepository: GetSelectedPatientRepository

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        useCase =
            GetFilteredRecordsUseCase(medicalRecordFilterRepository, getSelectedPatientRepository, coroutineContextProvider)
    }

    @Test
    fun `Filter records and group by date`() = runTest {
        // Given
        val request = FilterRequestDomainModel(
            groupBy = GroupByDomainModel.DATE,
            sortBy = SortByDomainModel.DESC,
            patientId = "1",
            selectedCategoryProblemIds = listOf(),
            selectedMedicalWorkerIds = listOf()
        )
        val willReturn = getFakeRecords()

        given(medicalRecordFilterRepository.filterRecords(request)).willReturn(willReturn)

        // When
        val actualValue = useCase.executeInBackground(request)
        val expected = listOf(
            GroupedItemsDomainModel(
                value = LocalDate.of(2020,1,1),
                items = listOf(
                    willReturn[0],willReturn[1]
                )
            ),
            GroupedItemsDomainModel(
                value = LocalDate.of(2020,2,1),
                items = listOf(
                    willReturn[2]
                )
            ),
            GroupedItemsDomainModel(
                value = LocalDate.of(2021,1,1),
                items = listOf(
                    willReturn[3]
                )
            ),
        )

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Filter records and group by problem category`() = runTest{
        // Given
        val request = FilterRequestDomainModel(
            groupBy = GroupByDomainModel.PROBLEM_CATEGORY,
            sortBy = SortByDomainModel.DESC,
            patientId = "1",
            selectedCategoryProblemIds = listOf(),
            selectedMedicalWorkerIds = listOf()
        )
        val willReturn = getFakeRecords()

        given(medicalRecordFilterRepository.filterRecords(request)).willReturn(willReturn)

        // When
        val actualValue = useCase.executeInBackground(request)
        val expected = listOf(
            GroupedItemsDomainModel(
                value = "Category 1",
                items = listOf(
                    willReturn[0],willReturn[2]
                )
            ),
            GroupedItemsDomainModel(
                value = "Category 2",
                items = listOf(
                    willReturn[1]
                )
            ),
            GroupedItemsDomainModel(
                value = "Category 3",
                items = listOf(
                    willReturn[3]
                )
            ),
        )

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Filter records and group by medical worker`() =runTest {
        // Given
        val request = FilterRequestDomainModel(
            groupBy = GroupByDomainModel.MEDICAL_WORKER,
            sortBy = SortByDomainModel.DESC,
            patientId = "1",
            selectedCategoryProblemIds = listOf(),
            selectedMedicalWorkerIds = listOf()
        )
        val willReturn = getFakeRecords()

        given(medicalRecordFilterRepository.filterRecords(request)).willReturn(willReturn)

        // When
        val actualValue = useCase.executeInBackground(request)
        val expected = listOf(
            GroupedItemsDomainModel(
                value = "Medical Worker 1",
                items = listOf(
                    willReturn[0]
                )
            ),
            GroupedItemsDomainModel(
                value = "Medical Worker 2",
                items = listOf(
                    willReturn[1], willReturn[3]
                )
            ),
            GroupedItemsDomainModel(
                value = "Medical Worker 3",
                items = listOf(
                    willReturn[2]
                )
            ),
        )

        // Then
        assertEquals(expected, actualValue)
    }

    private fun getFakeRecords(): List<MedicalRecordDomainModel> {
        return listOf(
            getMedicalRecord(
                date = LocalDate.of(2020, 1, 1),
                category = "Category 1",
                medicalWorker = "Medical Worker 1"
            ),
            getMedicalRecord(
                date = LocalDate.of(2020, 1, 1),
                category = "Category 2",
                medicalWorker = "Medical Worker 2"
            ),
            getMedicalRecord(
                date = LocalDate.of(2020, 2, 1),
                category = "Category 1",
                medicalWorker = "Medical Worker 3"
            ),
            getMedicalRecord(
                date = LocalDate.of(2021, 1, 1),
                category = "Category 3",
                medicalWorker = "Medical Worker 2"
            ),
        )
    }

    private fun getMedicalRecord(
        date: LocalDate,
        category: String,
        medicalWorker: String
    ): MedicalRecordDomainModel {
        val randomId = (0..100).random()
        return MedicalRecordDomainModel(
            id = randomId.toString(),
            patient = PatientDomainModel(
                id = "1",
                name = "Jan",
                birthDate = LocalDate.of(1990, 1, 1)
            ),
            problemCategory = ProblemCategoryDomainModel(
                id = "1",
                name = category,
                color = "#000000",
                patientId = "1"
            ),
            medicalWorker = MedicalWorkerDomainModel(
                id = "1",
                name = medicalWorker,
                patientId = "1"
            ),
            createdAt = date
        )
    }
}