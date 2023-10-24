package cz.vvoleman.phr.featureMedicine.domain.factory

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDate

class WeekdayDateFactoryTest {

    @Test
    fun testGetNextWeekdayDate() {
        // Given
        val currentDateTime = LocalDate.of(2023,10,24)
        val nextA = DayOfWeek.MONDAY
        val nextB = DayOfWeek.SATURDAY
        val nextC = currentDateTime.dayOfWeek

        // When
        val expectedA = LocalDate.of(2023,10,30)
        val expectedB = LocalDate.of(2023,10,28)
        val expectedC = LocalDate.of(2023,10,24)
        val resultA = WeekdayDateFactory.getNextWeekdayDate(nextA, currentDateTime)
        val resultB = WeekdayDateFactory.getNextWeekdayDate(nextB, currentDateTime)
        val resultC = WeekdayDateFactory.getNextWeekdayDate(nextC, currentDateTime)

        // Then
        assertEquals(expectedA, resultA)
        assertEquals(expectedB, resultB)
        assertEquals(expectedC, resultC)
    }

}