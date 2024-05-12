package cz.vvoleman.phr.common.ui.export.usecase

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import java.time.LocalDateTime

abstract class DocumentPage {

    private var _details: PageDetails? = null

    abstract fun bind(binding: ViewBinding)

    abstract fun getViewBinding(inflater: LayoutInflater): ViewBinding

    fun setDetails(details: PageDetails) {
        this._details = details
    }

    fun getDetails(): PageDetails? {
        return _details
    }

    data class PageDetails(
        val currentPage: Int,
        val totalPages: Int,
        val generatedAt: LocalDateTime = LocalDateTime.now()
    )
}
