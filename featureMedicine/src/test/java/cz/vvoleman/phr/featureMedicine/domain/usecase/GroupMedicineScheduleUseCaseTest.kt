package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
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
import java.time.LocalDateTime
import java.time.LocalTime

@ExtendWith(MockitoExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GroupMedicineScheduleUseCaseTest {

    private lateinit var useCase: GroupMedicineScheduleUseCase

    @Mock
    private lateinit var getSchedulesByPatientRepository: GetSchedulesByPatientRepository

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        useCase = GroupMedicineScheduleUseCase(
            getSchedulesByPatientRepository,
            coroutineContextProvider
        )
    }

    @Test
    fun `Get grouped schedules`() = runTest {
        // Given
        given(getSchedulesByPatientRepository.getSchedulesByPatient(PATIENT_ID)).willReturn(getFakeSchedules())

        // When
        val actualValue = useCase.executeInBackground(PATIENT_ID)

        // Then
        assertEquals(3, actualValue.size, "There should be 3 groups, ${actualValue.size} found")

        val firstGroup = actualValue.first()
        assertEquals("A", firstGroup.value, "First group should have name A, ${firstGroup.value} found")
        assertEquals(
            2,
            firstGroup.items.size,
            "First group should have 2 items, ${firstGroup.items.size} found"
        )
        assertEquals(
            "2",
            firstGroup.items[0].medicine.id,
            "Id of first item in first group should be 2, ${firstGroup.items[0].medicine.id} found"
        )

        val secondGroup = actualValue[1]
        assertEquals("Ž", secondGroup.value, "Second group should have name Ž, ${secondGroup.value} found")

        val thirdGroup = actualValue[2]
        assertEquals("-", thirdGroup.value, "Third group should have name -, ${thirdGroup.value} found")
        assertEquals(2, thirdGroup.items.size, "Third group should have 2 items, ${thirdGroup.items.size} found")
    }

    private fun getFakeSchedules(): List<MedicineScheduleDomainModel> {
        return listOf(
            makeSchedule(
                medicineId = "1",
                medicineName = "acylcoffin",
                weekDays = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                times = listOf(LocalTime.of(13, 0))
            ),
            makeSchedule(
                medicineId = "2",
                medicineName = "acylaněco",
                weekDays = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                times = listOf(LocalTime.of(16, 0))
            ),
            makeSchedule(
                medicineId = "3",
                medicineName = "Žyridoxin",
                weekDays = listOf(DayOfWeek.WEDNESDAY),
                times = listOf(LocalTime.of(8, 0), LocalTime.of(16, 0))
            ),
            makeSchedule(
                medicineId = "4",
                medicineName = "00Žyridoxin",
                weekDays = listOf(DayOfWeek.WEDNESDAY),
                times = listOf(LocalTime.of(8, 0), LocalTime.of(16, 0))
            ),
            makeSchedule(
                medicineId = "5",
                medicineName = "*Žyridoxin",
                weekDays = listOf(DayOfWeek.WEDNESDAY),
                times = listOf(LocalTime.of(8, 0), LocalTime.of(16, 0))
            ),
        )
    }

    private fun makeSchedule(
        medicineId: String,
        medicineName: String,
        weekDays: List<DayOfWeek>,
        times: List<LocalTime>
    ): MedicineScheduleDomainModel {
        val patientMock = mockk<PatientDomainModel> {
            every { id } returns PATIENT_ID
        }

        val medicineMock = mockk<MedicineDomainModel> {
            every { id } returns medicineId
            every { name } returns medicineName
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