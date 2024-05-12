package cz.vvoleman.featureMeasurement.domain.facade

import cz.vvoleman.phr.featureMeasurement.domain.facade.MeasurementTranslateDateTimeFacade
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupScheduleItemDomainModel
import cz.vvoleman.phr.featureMeasurement.domain.model.list.ScheduledMeasurementGroupDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

class MeasurementTranslateDateTimeFacadeTest {

    @Test
    fun `Test one week`() {
        val facade = MeasurementTranslateDateTimeFacade()
        val currentDateTime = LocalDateTime.of(2024, 1, 23, 7, 30)

        val groups = getGroups()
        val dtFirst = LocalDateTime.of(2024, 1, 23, 8, 0)
        val dtSecond = LocalDateTime.of(2024, 1, 24, 7, 0)
        val dtThird = LocalDateTime.of(2024, 1, 24, 8, 0)
        val dtFourth = LocalDateTime.of(2024, 1, 30, 7, 0)
        val expected = mapOf(
            Pair(
                dtFirst,
                listOf(
                    ScheduledMeasurementGroupDomainModel(groups[0], dtFirst),
                    ScheduledMeasurementGroupDomainModel(groups[1], dtFirst),
                )
            ),
            Pair(dtSecond, listOf(ScheduledMeasurementGroupDomainModel(groups[2], dtSecond))),
            Pair(dtThird, listOf(ScheduledMeasurementGroupDomainModel(groups[1], dtThird))),
            Pair(dtFourth, listOf(ScheduledMeasurementGroupDomainModel(groups[2], dtFourth))),
        )
        val result = facade.translate(groups, currentDateTime)

        assertEquals(expected.size, result.size)
        assertEquals(expected[dtFirst]?.size, result[dtFirst]?.size)
    }

    private fun getGroups(): List<MeasurementGroupDomainModel> {
        return listOf(
            makeGroup(
                groupId = "1",
                groupScheduleItems = listOf(
                    makeItem(
                        itemId = "1",
                        itemDayOfWeek = DayOfWeek.TUESDAY,
                        itemTime = LocalTime.of(8, 0),
                    ),
                    makeItem(
                        itemId = "2",
                        itemDayOfWeek = DayOfWeek.WEDNESDAY,
                        itemTime = LocalTime.of(8, 0),
                    ),
                )
            ),
            makeGroup(
                groupId = "2",
                groupScheduleItems = listOf(
                    makeItem(
                        itemId = "3",
                        itemDayOfWeek = DayOfWeek.TUESDAY,
                        itemTime = LocalTime.of(8, 0),
                    ),
                )
            ),
            makeGroup(
                groupId = "3",
                groupScheduleItems = listOf(
                    makeItem(
                        itemId = "4",
                        itemDayOfWeek = DayOfWeek.TUESDAY,
                        itemTime = LocalTime.of(7, 0),
                    ),
                    makeItem(
                        itemId = "5",
                        itemDayOfWeek = DayOfWeek.WEDNESDAY,
                        itemTime = LocalTime.of(7, 0),
                    ),
                )
            ),
        )
    }

    private fun makeGroup(
        groupId: String,
        groupScheduleItems: List<MeasurementGroupScheduleItemDomainModel>,
    ): MeasurementGroupDomainModel {
        return mockk(relaxed = true) {
            every { id } returns groupId
            every { scheduleItems } returns groupScheduleItems
        }
    }

    private fun makeItem(
        itemId: String,
        itemDayOfWeek: DayOfWeek,
        itemTime: LocalTime,
    ): MeasurementGroupScheduleItemDomainModel {
        return mockk(relaxed = true) {
            every { id } returns itemId
            every { dayOfWeek } returns itemDayOfWeek
            every { time } returns itemTime
        }
    }
}
