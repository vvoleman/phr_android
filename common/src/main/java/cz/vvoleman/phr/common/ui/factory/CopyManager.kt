package cz.vvoleman.phr.common.ui.factory

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.PersistableBundle
import android.widget.Toast
import cz.vvoleman.phr.common_datasource.R

class CopyManager(
    private val context: Context,
) {
    private val clipboardManager: ClipboardManager = context
        .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    fun copy(text: String, isSensitive: Boolean = false) {
        val clipData = createClipData(text, isSensitive)

        clipboardManager.setPrimaryClip(
            clipData
        )

        // Only show a toast for Android 12 and lower.
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.S) {
            val resources = context.resources
            Toast.makeText(context, resources.getString(R.string.copied), Toast.LENGTH_SHORT).show()
        }
    }

    private fun createClipData(text: String, isSensitive: Boolean): ClipData {
        val clipData = ClipData.newPlainText("", text)

        val isNewer = android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.S

        if (!isSensitive) {
            return clipData
        }

        if (isNewer) {
            clipData.description.extras = PersistableBundle().apply {
                putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
            }
        } else {
            clipData.apply {
                description.extras = PersistableBundle().apply {
                    putBoolean("android.content.extra.IS_SENSITIVE", true)
                }
            }
        }

        return clipData
    }
}
