package cz.vvoleman.phr.data.room.medicine.substance

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.core.Substance

@Entity(tableName = "substance")
data class SubstanceEntity(
    @PrimaryKey val substanceId: String,
    val name: String,
    val is_addictive: Boolean,
    val is_doping: Boolean
) {

    companion object {
        fun from(substance: Substance): SubstanceEntity {
            return SubstanceEntity(
                substanceId = substance.id,
                name = substance.name,
                is_addictive = substance.isAddictive,
                is_doping = substance.isDoping
            )
        }
    }

    fun toSubstance(): Substance {
        return Substance(
            id = substanceId,
            name = name,
            isAddictive = is_addictive,
            isDoping = is_doping
        )
    }
}
