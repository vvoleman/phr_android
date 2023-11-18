package cz.vvoleman.phr.featureMedicine.domain.usecase.export

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.export.GetDataForExportRequest
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.GetSchedulesByMedicineRepository
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import cz.vvoleman.phr.featureMedicine.test.coroutine.FakeCoroutineContextProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ExtendWith(MockitoExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetDataForExportUseCaseTest {

    private lateinit var useCase: GetDataForExportUseCase

    @Mock
    private lateinit var getSchedulesByPatientRepository: GetSchedulesByPatientRepository

    @Mock
    private lateinit var getSchedulesByMedicineRepository: GetSchedulesByMedicineRepository

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        useCase = GetDataForExportUseCase(
            getSchedulesByMedicineRepository,
            getSchedulesByPatientRepository,
            coroutineContextProvider
        )
    }

    @Test
    fun `Get data - Monday to null, medicine empty`() = runTest {
        // Given
        val request = GetDataForExportRequest(
            patientId = PATIENT_ID,
            currentDateTime = LocalTime.of(19, 0).atDate(LocalDate.of(2023, 11, 13)),
            medicine = emptyList(),
            dateRange = Pair(LocalDateTime.of(2023, 11, 6, 7, 0), null)
        )

        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        Assertions.assertEquals(3, actualValue.size, "Invalid size of export data")
        Assertions.assertEquals(
            6,
            actualValue.sumOf { it.schedules.size },
            "Invalid size of schedule items"
        )

        Assertions.assertEquals("2", actualValue.first().medicine.id, "Invalid medicine id of first schedule")

        Assertions.assertEquals("3", actualValue.last().medicine.id, "Invalid medicine id of last schedule")
    }

    @Test
    fun `Get data - Null to saturday, one medicine`() = runTest {
        // Given
        val medicineId = "1"
        val request = GetDataForExportRequest(
            patientId = PATIENT_ID,
            currentDateTime = LocalTime.of(19, 0).atDate(LocalDate.of(2023, 11, 13)),
            medicine = listOf(
                mockk {
                    every { id } returns medicineId
                }
            ),
            dateRange = Pair(null, LocalDateTime.of(2023, 11, 11, 7, 0))
        )

        given(getSchedulesByMedicineRepository.getSchedulesByMedicine(listOf(medicineId), PATIENT_ID)).willReturn(
            getFakeSchedules().filter { it.medicine.id == medicineId }
        )

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        Assertions.assertEquals(1, actualValue.size, "Invalid size of export data")
        Assertions.assertEquals(
            1,
            actualValue[0].schedules.size,
            "Invalid size of schedule items"
        )

        Assertions.assertEquals("1", actualValue[0].medicine.id, "Invalid medicine id of last schedule")
    }

    @Test
    fun `Get data - Null to null, two medicines`() = runTest {
        // Given
        val medicines = listOf("1", "3")
        val request = GetDataForExportRequest(
            patientId = PATIENT_ID,
            currentDateTime = LocalTime.of(19, 0).atDate(LocalDate.of(2023, 11, 13)),
            medicine = medicines.map { mockk { every { id } returns it } },
            dateRange = Pair(null, null)
        )

        given(getSchedulesByMedicineRepository.getSchedulesByMedicine(medicines, PATIENT_ID)).willReturn(
            getFakeSchedules().filter { medicines.contains(it.medicine.id) }
        )

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        Assertions.assertEquals(2, actualValue.size, "Invalid size of export data")
        Assertions.assertEquals(
            3,
            actualValue.sumOf { it.schedules.size },
            "Invalid size of schedule items"
        )

        Assertions.assertEquals("1", actualValue[0].medicine.id, "Invalid medicine id of last schedule")
    }

    private fun getFakeSchedules(): List<MedicineScheduleDomainModel> {
        return listOf(
            makeSchedule(
                medicineId = "1",
                createdAtMock = LocalDateTime.of(2023, 11, 8, 8, 0),
                weekDays = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                times = listOf(LocalTime.of(9, 0))
            ),
            makeSchedule(
                medicineId = "2",
                createdAtMock = LocalDateTime.of(2023, 11, 6, 8, 0),
                weekDays = listOf(DayOfWeek.MONDAY, DayOfWeek.FRIDAY),
                times = listOf(LocalTime.of(16, 0))
            ),
            makeSchedule(
                medicineId = "3",
                createdAtMock = LocalDateTime.of(2023, 11, 6, 8, 0),
                weekDays = listOf(DayOfWeek.SUNDAY),
                times = listOf(LocalTime.of(16, 0))
            ),
        )
    }

    private fun makeSchedule(
        medicineId: String,
        createdAtMock: LocalDateTime,
        weekDays: List<DayOfWeek>,
        times: List<LocalTime>
    ): MedicineScheduleDomainModel {
        val patientMock = mockk<PatientDomainModel> {
            every { id } returns PATIENT_ID
        }

        val medicineMock = mockk<MedicineDomainModel> {
            every { id } returns medicineId
            every { name } returns medicineId
        }

        val scheduleItems = mutableListOf<ScheduleItemDomainModel>()
        weekDays.forEach { day ->
            times.forEach { localTime ->
                scheduleItems.add(
                    mockk {
                        every { id } returns "${medicineId}_${day}_${localTime.hour}_${localTime.minute}"
                        every { dayOfWeek } returns day
                        every { time } returns localTime
                        every { quantity } returns 1
                        every { unit } returns ""
                    }
                )
            }
        }

        return mockk(relaxed = true) {
            every { id } returns (0..1000).random().toString()
            every { patient } returns patientMock
            every { medicine } returns medicineMock
            every { schedules } returns scheduleItems
            every { isAlarmEnabled } returns true
            every { createdAt } returns createdAtMock
        }
    }

    companion object {
        private const val PATIENT_ID = "1"
    }
}
