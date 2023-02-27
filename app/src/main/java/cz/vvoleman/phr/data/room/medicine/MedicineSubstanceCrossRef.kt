package cz.vvoleman.phr.data.room.medicine

import androidx.room.Entity

@Entity(primaryKeys = ["medicineId", "substanceId"])
data class MedicineSubstanceCrossRef(
    val medicineId: String,
    val substanceId: String
)