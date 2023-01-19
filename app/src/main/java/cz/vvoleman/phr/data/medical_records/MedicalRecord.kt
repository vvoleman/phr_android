package cz.vvoleman.phr.data.medical_records

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date

@Entity(tableName = "medical_records")
@Parcelize
data class MedicalRecord(
    @PrimaryKey(autoGenerate = true) val recordId: Int = 0,
    val facilityId: Int,
    val patientId: Int,
    val diagnoseId: String,
    val date: Date,
    val text: String
) : Parcelable {

}