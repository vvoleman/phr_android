package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class PositionTest {

    @Test
    fun `getConvexHull of 4 points`() {
        // Preparation
        val points = listOf(
            Position(1, 0),
            Position(3, 1),
            Position(1, 2),
            Position(0, 3)
        )

        val expectedHull = listOf(
            Position(1, 0),
            Position(3, 1),
            Position(0,3)
        )

        // Execution
        val actualHull = Position.getConvexHull(points)

        val actualSorted = actualHull.sorted()
        val expectedSorted = expectedHull.sorted()

        // Verification
        assertEquals(expectedSorted, actualSorted)
    }

    @Test
    fun `getConvexHull of collinear points`() {
        // Preparation
        val points = listOf(
            Position(1, 1),
            Position(2, 2),
            Position(3, 3),
            Position(4, 4)
        )

        val expectedHull = listOf(
            Position(1, 1),
            Position(4, 4)
        )

        // Execution
        val actualHull = Position.getConvexHull(points)

        // Verification
        assertEquals(expectedHull, actualHull)
    }

    @Test
    fun `getConvexHull of duplicate points`() {
        // Preparation
        val points = listOf(
            Position(1, 1),
            Position(2, 2),
            Position(3, 3),
            Position(2, 2),
            Position(4, 4)
        )

        val expectedHull = listOf(
            Position(1, 1),
            Position(4, 4)
        )

        // Execution
        val actualHull = Position.getConvexHull(points)

        // Verification
        assertEquals(expectedHull, actualHull)
    }

    @Test
    fun `getConvexHull of less than three points`() {
        // Preparation
        val points = listOf(
            Position(1, 1),
            Position(2, 2)
        )

        // Execution and Verification
        assertEquals(emptyList<Position>(), Position.getConvexHull(points))
    }

}