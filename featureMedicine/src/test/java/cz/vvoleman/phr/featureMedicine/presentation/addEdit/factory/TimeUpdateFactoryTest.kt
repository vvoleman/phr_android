package cz.vvoleman.phr.featureMedicine.presentation.addEdit.factory

import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.TimePresentationModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalTime

class TimeUpdateFactoryTest {

    @Test
    fun testGroupTimes() {
        val listA = listOf(
            TimePresentationModel(LocalTime.of(10, 0, 0), 3),
            TimePresentationModel(LocalTime.of(10, 0, 0), 2)
        )

        val listB = listOf(
            TimePresentationModel(LocalTime.of(10, 0, 0), 3),
            TimePresentationModel(LocalTime.of(10, 10, 0), 2)
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