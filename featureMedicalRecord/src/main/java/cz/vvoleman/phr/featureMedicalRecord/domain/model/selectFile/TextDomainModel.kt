package cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextDomainModel(
    val cornerPoints: List<Position> = emptyList(),
    val texts: List<TextDomainModel>,
    val value: String? = null,
    private val previousText: String? = null
) : Parcelable {
    fun getText(): String {
        if (previousText != null || value != null) {
            return previousText ?: value!!
        }

        var text = ""
        for (t in texts) {
            text += t.getText()
        }
        return text
    }
}
