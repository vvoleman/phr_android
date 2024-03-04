package cz.vvoleman.phr.common.ui.export.usecase

class DocumentFactory(
    private val helper: ExportPdfHelper,
    private val pages: List<DocumentPage> = listOf()
) {

    init {
        for (i in pages.indices) {
            pages[i].setDetails(DocumentPage.PageDetails(i + 1, pages.size))
        }
    }

    fun generate() {
        val inflater = helper.getInflater()

        pages.forEach { page ->
            val view = page.getViewBinding(inflater)
            page.bind(view)
            helper.addPage(view.root)
        }

        helper.run()
    }
}
