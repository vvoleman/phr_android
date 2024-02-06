package cz.vvoleman.phr.common.ui.export.usecase

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.core.content.ContextCompat
import cz.vvoleman.phr.base.ui.exception.PermissionDeniedException
import cz.vvoleman.phr.common.domain.model.export.ExportType
import cz.vvoleman.phr.common.presentation.model.export.PermissionStatus
import cz.vvoleman.phr.common.ui.export.exception.ExportFailedException

abstract class ExportFileHelper(
    protected val context: Context,
    private val createFileLauncher: ActivityResultLauncher<String>,
    private val permissionsLauncher: ActivityResultLauncher<Array<String>>,
) {
    private var readPermissionGranted = false
    private var writePermissionGranted = false

    abstract val exportType: ExportType


    abstract suspend fun handleCreateFileResult(uri: Uri?)

    abstract fun getDefaultFileName(): String

    /**
     * @throws ExportFailedException
     */
    protected abstract fun generate()

    /**
     * @throws ExportFailedException
     * @throws PermissionDeniedException
     */
    fun run() {
        val permissionSize = updateOrRequestPermissions()

        if (permissionSize == 0) {
            execute()
        }
    }

    fun hasPermissions(): PermissionStatus {
        return PermissionStatus.get(readPermissionGranted, writePermissionGranted)
    }

    fun handlePermissionResult(permissions: Map<String, Boolean>) {
        readPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: readPermissionGranted
        writePermissionGranted =
            permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: writePermissionGranted

//        execute(params!!)
    }

    protected fun execute() {
        val hasPermissions = hasPermissions()
        if (hasPermissions is PermissionStatus.Denied) {
            Log.e("ExportFileHelper", "Export failed, API level: ${Build.VERSION.SDK_INT}")
            throw PermissionDeniedException(
                "Read permission: ${hasPermissions.readPermission}, write permission: ${hasPermissions.writePermission}"
            )
        }

        runCatching { generate() }.getOrElse { throw ExportFailedException(it) }

        createFileLauncher.launch(getDefaultFileName())
    }

    private fun updateOrRequestPermissions(): Int {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val hasWritePermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || minSdk29

        val permissionsToRequest = mutableListOf<String>()
        if (!writePermissionGranted) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!readPermissionGranted) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }

        return permissionsToRequest.size
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q, lambda = 0)
    protected fun <T> sdk29AndUp(onSdk29: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            onSdk29()
        } else {
            null
        }
    }
}
