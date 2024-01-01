package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.SchedulesInRangeRequest
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import cz.vvoleman.phr.featureMedicine.test.coroutine.FakeCoroutineContextProvider
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ExtendWith(MockitoExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetScheduledInTimeRangeUseCaseTest {

    private lateinit var useCase: GetScheduledInTimeRangeUseCase

    @Mock
    private lateinit var getSchedulesByPatientRepository: GetSchedulesByPatientRepository

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        useCase = GetScheduledInTimeRangeUseCase(
            getSchedulesByPatientRepository,
            coroutineContextProvider
        )
    }

    @Test
    fun `Get scheduled in time range in one day`() = runTest {
        // Given
        val request = SchedulesInRangeRequest(
            patientId = PATIENT_ID,
            startAt = LocalTime.of(8, 0).atDate(LocalDate.of(2023, 10, 25)),
            endAt = LocalTime.of(15, 59).atDate(LocalDate.of(2023, 10, 25)),
        )

        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertEquals(2, actualValue.size)
        assertEquals("3", actualValue[0].medicine.id)
        assertEquals("1", actualValue[1].medicine.id)
    }

    @Test
    fun `Get scheduled in range of two days`() = runTest {
        // Given
        val request = SchedulesInRangeRequest(
            patientId = PATIENT_ID,
            startAt = LocalTime.MIN.atDate(LocalDate.of(2023, 10, 23)),
            endAt = LocalTime.MAX.atDate(LocalDate.of(2023, 10, 24)),
        )

        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertEquals(3, actualValue.size, "List of scheduled medicine should have 3 items, have ${actualValue.size}")

        assertEquals("1", actualValue[0].medicine.id, "First scheduled medicine should be medicine with id 1")
        assertEquals("2", actualValue[1].medicine.id, "Second scheduled medicine should be medicine with id 2")
        assertEquals("2", actualValue[2].medicine.id, "Third scheduled medicine should be medicine with id 2")
    }

    @Test
    fun `Get scheduled across week edge`() = runTest {
        // Given
        val request = SchedulesInRangeRequest(
            patientId = PATIENT_ID,
            startAt = LocalTime.MIN.atDate(LocalDate.of(2023, 10, 23)),
            endAt = LocalTime.MAX.atDate(LocalDate.of(2023, 10, 30)),
        )

        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertEquals(8, actualValue.size, "List of scheduled medicine should have 8 items, have ${actualValue.size}")
    }

    @Test
    fun `Get scheduled across multiple weeks`() = runTest {
        // Given
        val request = SchedulesInRangeRequest(
            patientId = PATIENT_ID,
            startAt = LocalTime.MIN.atDate(LocalDate.of(2023, 10, 23)),
            endAt = LocalTime.MAX.atDate(LocalDate.of(2023, 11, 10)),
        )

        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertEquals(18, actualValue.size, "List of scheduled medicine should have 18 items, have ${actualValue.size}")
    }

    private fun getFakeSchedules(): List<MedicineScheduleDomainModel> {
        return listOf(
            makeSchedule(
                medicineId = "1",
                weekDays = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                times = listOf(LocalTime.of(13, 0))
            ),
            makeSchedule(
                medicineId = "2",
                weekDays = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                times = listOf(LocalTime.of(16, 0))
            ),
            makeSchedule(
                medicineId = "3",
                weekDays = listOf(DayOfWeek.WEDNESDAY),
                times = listOf(LocalTime.of(8, 0), LocalTime.of(16, 0))
            ),
        )
    }

    private fun makeSchedule(
        medicineId: String,
        weekDays: List<DayOfWeek>,
        times: List<LocalTime>
    ): MedicineScheduleDomainModel {
        val patientMock = mockk<PatientDomainModel> {
            every { id } returns PATIENT_ID
        }

        val medicineMock = mockk<MedicineDomainModel> {
            every { id } returns medicineId
        }

        val scheduleItems = mutableListOf<ScheduleItemDomainModel>()
        weekDays.forEach { day ->
            times.forEach { localTime ->
                scheduleItems.add(
                    mockk {
                        every { id } returns "${medicineId}_${day}_${localTime.hour}_${localTime.minute}"
                        every { dayOfWeek } returns day
                        every { time } returns localTime
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
        private val createdAtMock = LocalDateTime.of(2023, 10, 24, 12, 0)
    }
}
