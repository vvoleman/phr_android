package cz.vvoleman.phr.medicine.data

data class Medicine (
    val id: Int,
    val name: String,
    val dosage: String,
    val info: String,
    val relativeTime: String
) {
    val relativeTimeFormatted: String
        get() = relativeTime.replace(" ", "\u00A0")
}