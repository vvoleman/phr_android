package cz.vvoleman.phr.data.problem_category

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "problem_categories")
@Parcelize
data class ProblemCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
) : Parcelable