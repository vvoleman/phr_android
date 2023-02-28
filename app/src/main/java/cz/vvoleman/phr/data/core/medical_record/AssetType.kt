package cz.vvoleman.phr.data.core.medical_record

enum class AssetType {
    IMAGE,
    DOCUMENT;

    companion object {
        fun fromString(type: String): AssetType {
            return when (type) {
                "IMAGE" -> IMAGE
                "DOCUMENT" -> DOCUMENT
                else -> throw IllegalArgumentException("Unknown asset type: $type")
            }
        }
    }

}