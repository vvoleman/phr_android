package cz.vvoleman.phr.data.diagnose

import androidx.room.PrimaryKey

data class Diagnose(
    @PrimaryKey val id: Int,
    val name: String,
)
