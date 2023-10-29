package cz.vvoleman.phr.featureMedicine.domain.factory

import cz.vvoleman.phr.common.utils.plusDayOfWeek
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDate

class WeekdayDateFactoryTest {

    @Test
    fun testGetNextWeekdayDate() {
        // Given
        val currentDate = LocalDate.of(2023,10,24)
        val nextA = DayOfWeek.MONDAY
        val nextB = DayOfWeek.SATURDAY
        val nextC = currentDate.dayOfWeek

        // When
        val expectedA = LocalDate.of(2023,10,30)
        val expectedB = LocalDate.of(2023,10,28)
        val expectedC = LocalDate.of(2023,10,24)
        val resultA = currentDate.plusDayOfWeek(nextA)
        val resultB = currentDate.plusDayOfWeek(nextB)
        val resultC = currentDate.plusDayOfWeek(nextC)

        // Then
        assertEquals(expectedA, resultA)
        assertEquals(expectedB, resultB)
        assertEquals(expectedC, resultC)
    }

}