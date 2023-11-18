package cz.vvoleman.phr.featureMedicine.ui.export.usecase

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import cz.vvoleman.phr.featureMedicine.domain.model.export.ExportType
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportParamsPresentationModel
import cz.vvoleman.phr.featureMedicine.ui.export.exception.ExportFailedException
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneOffset

class ExportPdfHelper(
    private val layoutResId: Int,
    private val listener: ExportPdfHelperListener,
    context: Context,
    createFileLauncher: ActivityResultLauncher<String>,
    permissionsLauncher: ActivityResultLauncher<Array<String>>,
) : ExportFileHelper(context, createFileLauncher, permissionsLauncher) {

    override val exportType = ExportType.PDF

    private var _document: PdfDocument? = null

    override suspend fun handleCreateFileResult(uri: Uri?) {
        if (uri != null && _document != null) {
            try {
                val fileDescriptor = context.contentResolver?.openFileDescriptor(uri, "w")
                fileDescriptor?.use { fd ->
                    val outputStream = FileOutputStream(fd.fileDescriptor)
                    _document!!.writeTo(outputStream)
                    _document!!.close()
                }
            } catch (e: IOException) {
                throw ExportFailedException(e)
            }
        }
    }

    override fun getDefaultFileName(): String {
        return "export_${LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)}.pdf"
    }

    override fun generate(params: ExportParamsPresentationModel) {
        // Create a PdfDocument
        val pdfDocument = PdfDocument()

        // Inflate the XML layout
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(layoutResId, null)
        listener.bindPdf(view, params, pdfDocument)

        // Measure and layout the view
        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, PDF_PAGE_WIDTH, PDF_PAGE_HEIGHT)

        // Create a PDF page
        val pageInfo = PdfDocument.PageInfo.Builder(view.width, view.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Draw the view on the PDF page
        view.draw(canvas)

        // Finish the page
        pdfDocument.finishPage(page)

        _document = pdfDocument
    }
    companion object {
        private const val PDF_PAGE_WIDTH = 992
        private const val PDF_PAGE_HEIGHT = 1403
    }

    interface ExportPdfHelperListener {
        fun bindPdf(view: View, params: ExportParamsPresentationModel, pdfDocument: PdfDocument)
    }
}
