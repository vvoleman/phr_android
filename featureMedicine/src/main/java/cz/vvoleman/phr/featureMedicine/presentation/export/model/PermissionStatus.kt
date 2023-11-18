package cz.vvoleman.phr.featureMedicine.presentation.export.model

sealed class PermissionStatus {
    object Granted : PermissionStatus()
    data class Denied(
        val readPermission: Boolean,
        val writePermission: Boolean
    ) : PermissionStatus()

    companion object {
        fun get(read: Boolean, write: Boolean) = if (read && write) {
            Granted
        } else {
            Denied(read, write)
        }
    }
}
