package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import cz.vvoleman.phr.featureMedicine.test.coroutine.FakeCoroutineContextProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
class GetNextScheduledUseCaseTest {

    private lateinit var useCase: GetNextScheduledUseCase

    @Mock
    private lateinit var getSchedulesByPatientRepository: GetSchedulesByPatientRepository

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        useCase = GetNextScheduledUseCase(
            getSchedulesByPatientRepository,
            coroutineContextProvider
        )
    }

    @Test
    fun `Get next scheduled medicine`() = runTest {
        // Given
        val request = NextScheduledRequestDomainModel(
            patientId = PATIENT_ID,
            currentLocalDateTime = LocalTime.of(7, 0).atDate(LocalDate.of(2023, 10, 25)),
            limit = 5,
        )

        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertTrue(actualValue.isNotEmpty(), "List of next scheduled medicine should not be empty")
        assertTrue(
            actualValue.size == 5,
            "List of next scheduled medicine should have 5 items, have ${actualValue.size}"
        )

        val next = actualValue[0]
        assertEquals("3", next.medicine.id, "Next scheduled medicine should be medicine with id 3")

        assertTrue(next.scheduleItem.time == LocalTime.of(8,0), "Next scheduled medicine should be at 8:00")
        assertTrue(next.scheduleItem.dayOfWeek == DayOfWeek.WEDNESDAY, "Next scheduled medicine should be at wednesday")
    }

    @Test
    fun `Get next schedule across week end`() = runTest {
        // Given
        val request = NextScheduledRequestDomainModel(
            patientId = PATIENT_ID,
            currentLocalDateTime = LocalTime.of(7, 0).atDate(LocalDate.of(2023, 10, 28)),
            limit = 5,
        )

        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertTrue(actualValue.isNotEmpty(), "List of next scheduled medicine should not be empty")
        assertTrue(
            actualValue.size == 5,
            "List of next scheduled medicine should have 5 items, have ${actualValue.size}"
        )

        val next = actualValue[0]
        assertEquals("1", next.medicine.id, "Next scheduled medicine should be medicine with id 1")

        assertTrue(next.scheduleItem.time == LocalTime.of(13,0), "Next scheduled medicine should be at 13:00")
    }

    @Test
    fun `Get next schedule in a middle of a day`() = runTest {
        // Given
        val request = NextScheduledRequestDomainModel(
            patientId = PATIENT_ID,
            currentLocalDateTime = LocalTime.of(10, 0).atDate(LocalDate.of(2023, 10, 25)),
            limit = 5,
        )

        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertTrue(actualValue.isNotEmpty(), "List of next scheduled medicine should not be empty")
        assertTrue(
            actualValue.size == 5,
            "List of next scheduled medicine should have 5 items, have ${actualValue.size}"
        )

        val next = actualValue[0]
        assertEquals("1", next.medicine.id, "Next scheduled medicine should be medicine with id 1")

        assertTrue(next.scheduleItem.time == LocalTime.of(13,0), "Next scheduled medicine should be at 13:00")
    }

    @Test
    fun `Get next schedule when multiple schedules at the same time`() = runTest {
        // Given
        val request = NextScheduledRequestDomainModel(
            patientId = PATIENT_ID,
            currentLocalDateTime = LocalTime.of(15, 0).atDate(LocalDate.of(2023, 10, 25)),
            limit = 2,
        )

        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertTrue(actualValue.isNotEmpty(), "List of next scheduled medicine should not be empty")
        assertTrue(
            actualValue.size == 2,
            "List of next scheduled medicine should have 2 items, have ${actualValue.size}"
        )

//        val timesA = actualValue[0].schedules.map { it.time }
//        val timesB = actualValue[1].schedules.map { it.time }
//
//        assertTrue(timesA.contains(LocalTime.of(16, 0)), "Next scheduled medicine should be at 16:00, but was $timesA")
//        assertTrue(timesB.contains(LocalTime.of(16, 0)), "Next scheduled medicine should be at 16:00, but was $timesB")
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
                weekDays = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
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