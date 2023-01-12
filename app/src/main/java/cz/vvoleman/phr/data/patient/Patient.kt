package cz.vvoleman.phr.data.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val info: String?
)
