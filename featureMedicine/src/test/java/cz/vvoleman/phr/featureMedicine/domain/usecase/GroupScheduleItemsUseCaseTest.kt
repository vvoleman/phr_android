package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.featureMedicine.domain.facade.TranslateDateTimeFacade
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.ScheduleItemWithDetailsDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.GroupScheduleItemsRequest
import cz.vvoleman.phr.featureMedicine.test.coroutine.FakeCoroutineContextProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@ExtendWith(MockitoExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GroupScheduleItemsUseCaseTest {

    private lateinit var useCase: GroupScheduleItemsUseCase

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider

        useCase = GroupScheduleItemsUseCase(coroutineContextProvider)
    }

    @Test
    fun `Group schedule items, same day`() = runTest {
        // Given
        val request = GroupScheduleItemsRequest(
            scheduleItems = getFakeSameDay(),
            currentDateTime = currentDateTime
        )

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertEquals(2, actualValue.size)

        assertEquals(3, actualValue[0].items.size)
        assertEquals("1", actualValue[0].items[0].scheduleItem.id)
        assertEquals("Cc", actualValue[0].items[1].medicine.name)
    }

    @Test
    fun `Group schedule items, multiple days`() = runTest {
        // Given
        val request = GroupScheduleItemsRequest(
            scheduleItems = getFakeMultipleDays(),
            currentDateTime = currentDateTime
        )

        // When
        val actualValue = useCase.executeInBackground(request)

        // Then
        assertEquals(5, actualValue.size)

        assertEquals(1, actualValue[0].items.size)
        assertEquals("10", actualValue[0].items[0].scheduleItem.id)
        assertEquals("1", actualValue.last().items[0].scheduleItem.id)
    }

    private fun getFakeSameDay(): List<ScheduleItemWithDetailsDomainModel> {
        return listOf(
            makeSchedule(
                scheduleItemId = "1",
                medicineName = "Aa",
                weekDay = DayOfWeek.WEDNESDAY,
                localTime = LocalTime.of(8, 10)
            ),
            makeSchedule(
                scheduleItemId = "2",
                medicineName = "Cc",
                weekDay = DayOfWeek.WEDNESDAY,
                localTime = LocalTime.of(8, 15)
            ),
            makeSchedule(
                scheduleItemId = "3",
                medicineName = "Bb",
                weekDay = DayOfWeek.WEDNESDAY,
                localTime = LocalTime.of(8, 30)
            ),
            makeSchedule(
                scheduleItemId = "4",
                medicineName = "Dd",
                weekDay = DayOfWeek.WEDNESDAY,
                localTime = LocalTime.of(9, 30)
            ),
        )
    }

    private fun getFakeMultipleDays(): List<ScheduleItemWithDetailsDomainModel> {
        return listOf(
            makeSchedule(
                scheduleItemId = "1",
                medicineName = "Aa",
                weekDay = DayOfWeek.WEDNESDAY,
                localTime = LocalTime.of(8, 0)
            ),
            makeSchedule(
                scheduleItemId = "10",
                medicineName = "AaB",
                weekDay = DayOfWeek.WEDNESDAY,
                localTime = LocalTime.of(8, 10)
            ),
            makeSchedule(
                scheduleItemId = "2",
                medicineName = "Cc",
                weekDay = DayOfWeek.THURSDAY,
                localTime = LocalTime.of(8, 0)
            ),
            makeSchedule(
                scheduleItemId = "3",
                medicineName = "Bb",
                weekDay = DayOfWeek.FRIDAY,
                localTime = LocalTime.of(8, 30)
            ),
            makeSchedule(
                scheduleItemId = "4",
                medicineName = "Dd",
                weekDay = DayOfWeek.WEDNESDAY,
                localTime = LocalTime.of(9, 30)
            ),
        )
    }

    private fun makeSchedule(
        scheduleItemId: String,
        medicineName: String,
        weekDay: DayOfWeek,
        localTime: LocalTime,
    ): ScheduleItemWithDetailsDomainModel {
        val medicineMock = mockk<MedicineDomainModel> {
            every { name } returns medicineName
        }

        val itemMock = mockk<ScheduleItemDomainModel> {
            every { id } returns scheduleItemId
            every { dayOfWeek } returns weekDay
            every { time } returns localTime
            every { getTranslatedDateTime(currentDateTime) } returns TranslateDateTimeFacade.translateScheduleItem(
                this,
                currentDateTime
            )
        }

        return mockk(relaxed = true) {
            every { scheduleItem } returns itemMock
            every { medicine } returns medicineMock
        }
    }

    companion object {
        private val currentDateTime = LocalTime.of(8, 5).atDate(LocalDate.of(2023, 10, 25))
    }

}