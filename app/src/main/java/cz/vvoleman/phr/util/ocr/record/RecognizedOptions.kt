package cz.vvoleman.phr.util.ocr.record

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecognizedOptions(
    val visitDate: List<String>,
    val diagnose: List<String>,
    val doctor: List<String>,
    val patient: List<String>,
) : Parcelable {}
