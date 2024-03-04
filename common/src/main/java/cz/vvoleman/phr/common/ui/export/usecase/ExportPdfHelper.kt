package cz.vvoleman.phr.common.ui.export.usecase

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import cz.vvoleman.phr.common.domain.model.export.ExportType
import cz.vvoleman.phr.common.ui.export.exception.ExportFailedException
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneOffset

class ExportPdfHelper(
    context: Context,
    createFileLauncher: ActivityResultLauncher<String>,
    permissionsLauncher: ActivityResultLauncher<Array<String>>,
) : ExportFileHelper(context, createFileLauncher, permissionsLauncher) {

    override val exportType = ExportType.PDF

    public var _document: PdfDocument = PdfDocument()

    private var _currentPage: Int = 0

    var path: String? = null

    private val currentPage: Int
        get() = _currentPage

    override suspend fun handleCreateFileResult(uri: Uri?) {
        if (uri != null) {
            try {
                val fileDescriptor = context.contentResolver?.openFileDescriptor(uri, "w")
                fileDescriptor?.use { fd ->
                    val outputStream = FileOutputStream(fd.fileDescriptor)
                    _document.writeTo(outputStream)
                    _document.close()

                    path = uri.toString()
                }
            } catch (e: IOException) {
                throw ExportFailedException(e)
            }
        }
    }

    override fun getDefaultFileName(): String {
        return "export_${LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)}.pdf"
    }

    override fun generate() {
        // Do nothing?
    }

    fun addPage(view: View) {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(PDF_PAGE_WIDTH, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(PDF_PAGE_HEIGHT, View.MeasureSpec.EXACTLY)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)

        val pageInfo = PdfDocument.PageInfo.Builder(view.width, view.height, ++_currentPage).create()
        val page = _document.startPage(pageInfo)
        val canvas = page.canvas
        view.draw(canvas)
        _document.finishPage(page)
    }

    fun getInflater(): LayoutInflater {
        return LayoutInflater.from(context)
    }

    companion object {
        private const val PDF_PAGE_WIDTH = 595
        private const val PDF_PAGE_HEIGHT = 842
    }
}
