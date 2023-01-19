package cz.vvoleman.phr.data.patient

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.AdapterPair

@Entity
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val info: String?
)  {
    override fun toString(): String {
        return name
    }

    fun getAdapterPair(): AdapterPair {
        return AdapterPair(id.toString(), name, this)
    }
}
