package cz.vvoleman.phr.data.diagnose

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.AdapterPair

@Entity(tableName = "diagnoses")
data class Diagnose(
    @PrimaryKey val id: String,
    val name: String,
    val parent: String
) {

    override fun toString(): String {
        return name
    }

    fun getAdapterPair(): AdapterPair {
        return AdapterPair(id, name, this)
    }

}



