package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Suppress("MagicNumber")
@Parcelize
data class Position(
    val x: Int,
    val y: Int
) : Comparable<Position>, Parcelable {

    override fun compareTo(other: Position): Int {
        return if (this.x == other.x) {
            this.y.compareTo(other.y)
        } else {
            this.x.compareTo(other.x)
        }
    }

    companion object {
        fun getConvexHull(p: List<Position>): List<Position> {
            if (p.size <= 3) {
                return if (p.size == 3) p else emptyList()
            }

            val sorted = p.sorted()
            val h = mutableListOf<Position>()

            // lower hull
            for (pt in sorted) {
                while (h.size >= 2 && !ccw(h[h.size - 2], h.last(), pt)) {
                    h.removeAt(h.lastIndex)
                }
                h.add(pt)
            }

            // upper hull
            val t = h.size + 1
            for (i in sorted.size - 2 downTo 0) {
                val pt = sorted[i]
                while (h.size >= t && !ccw(h[h.size - 2], h.last(), pt)) {
                    h.removeAt(h.lastIndex)
                }
                h.add(pt)
            }

            h.removeAt(h.lastIndex)
            return h
        }

        /* ccw returns true if the three points make a counter-clockwise turn */
        fun ccw(a: Position, b: Position, c: Position) =
            ((b.x - a.x) * (c.y - a.y)) > ((b.y - a.y) * (c.x - a.x))
    }
}
