package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

data class Position(
    val x: Int,
    val y: Int,
): Comparable<Position> {

    override fun compareTo(other: Position): Int {
        return if (this.x == other.x) {
            this.y.compareTo(other.y)
        } else {
            this.x.compareTo(other.x)
        }
    }

    companion object {
        fun getConvexHull(p: List<Position>): List<Position> {
            if (p.isEmpty() || p.size < 3) return emptyList()
            if (p.size == 3) return p
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
