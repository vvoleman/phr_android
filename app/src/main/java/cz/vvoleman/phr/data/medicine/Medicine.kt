package cz.vvoleman.phr.data.medicine

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
data class Medicine (
    @PrimaryKey val id: Int,
    val name: String,
    val dosage: String,
    val info: String,
    @ColumnInfo(name="expires_at") val expiresAt: String?,
    //nullable string
    @ColumnInfo(name="created_at") val createdAt: String?,
) {
}