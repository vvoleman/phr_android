package cz.vvoleman.phr.featureMedicine.presentation.addEdit.factory

import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.TimePresentationModel
import org.junit.Assert.assertEquals
import java.time.LocalTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TimeUpdateFactoryTest {

    @Test
    fun testGroupTimes() {
        val listA = listOf(
            TimePresentationModel("a", LocalTime.of(10, 0, 0), 3),
            TimePresentationModel("b", LocalTime.of(10, 0, 0), 2)
        )

        val listB = listOf(
            TimePresentationModel("a", LocalTime.of(10, 0, 0), 3),
            TimePresentationModel("b", LocalTime.of(10, 10, 0), 2)
        )

        val resultA = TimeUpdateFactory.groupTimes(listA)
        val resultB = TimeUpdateFactory.groupTimes(listB)

        assertEquals(1, resultA.size)
        assertEquals((5).toDouble(), resultA[0].number)

        assertEquals(2, resultB.size)
        assertEquals(3, resultB[0].number)
        assertEquals(2, resultB[1].number)
    }

}