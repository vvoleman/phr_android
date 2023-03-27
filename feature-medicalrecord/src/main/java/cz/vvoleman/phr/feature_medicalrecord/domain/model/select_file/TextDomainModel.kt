package cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file

data class TextDomainModel(
    val cornerPoints: List<Position> = emptyList(),
    val texts: List<TextDomainModel>,
    val value: String? = null,
    private val _text: String? = null
) {
    fun getText(): String {
        if (_text != null) {
            return _text
        }

        if (value != null) {
            return value
        }

        var text = ""
        for (t in texts) {
            text += t.getText()
        }
        return text
    }
}