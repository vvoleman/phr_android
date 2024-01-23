package cz.vvoleman.phr.common.domain.facade

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

class TranslateDateTimeFacadeTest {

    @Test
    fun testTranslateDateTime() {
        val facade = TranslateDateTimeFacade()
        val currentWeekDay = currentDateTime.dayOfWeek

        val testValues = getTestValues()

        testValues.forEach {
            val resultValue = facade.translateScheduleItem(
                scheduleDayOfWeek = it.scheduleDayOfWeek,
                scheduleTime = it.scheduleTime,
                currentWeekDay = currentWeekDay,
                currentDateTime = currentDateTime,
                weekMultiplier = it.weekMultiplier
            )

            assertEquals(it.expected, resultValue, it.name)
        }
    }

    private fun getTestValues(): List<TestValue> {
        return listOf(
            TestValue(
                name = "Same day, before current time",
                scheduleDayOfWeek = DayOfWeek.TUESDAY,
                scheduleTime = LocalTime.of(7, 30),
                weekMultiplier = 0,
                expected = currentDateTime.toLocalDate().plusDays(7).atTime(7, 30)
            ),
            TestValue(
                name = "Same day, after current time",
                scheduleDayOfWeek = DayOfWeek.TUESDAY,
                scheduleTime = LocalTime.of(9, 30),
                weekMultiplier = 0,
                expected = currentDateTime.toLocalDate().atTime(9, 30)
            ),
            TestValue(
                name = "Same week, no weekMultiplier",
                scheduleDayOfWeek = DayOfWeek.WEDNESDAY,
                scheduleTime = LocalTime.of(7, 30),
                weekMultiplier = 0,
                expected = currentDateTime.toLocalDate().plusDays(1).atTime(7, 30)
            ),
            TestValue(
                name = "Next week, no weekMultiplier",
                scheduleDayOfWeek = DayOfWeek.MONDAY,
                scheduleTime = LocalTime.of(7, 30),
                weekMultiplier = 0,
                expected = currentDateTime.toLocalDate().plusDays(6).atTime(7, 30)
            ),
            TestValue(
                name = "Next week, weekMultiplier",
                scheduleDayOfWeek = DayOfWeek.WEDNESDAY,
                scheduleTime = LocalTime.of(7, 30),
                weekMultiplier = 1,
                expected = currentDateTime.toLocalDate().plusDays(8).atTime(7, 30)
            ),
        )
    }

    private data class TestValue(
        val name: String,
        val scheduleDayOfWeek: DayOfWeek,
        val scheduleTime: LocalTime,
        val weekMultiplier: Int = 0,
        val expected: LocalDateTime
    )

    companion object {
        private val currentDateTime = LocalDateTime.of(2024, 1, 23, 8, 0)
    }

}
