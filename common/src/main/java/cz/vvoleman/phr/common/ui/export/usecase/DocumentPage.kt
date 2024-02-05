package cz.vvoleman.phr.common.ui.export.usecase

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

abstract class DocumentPage {

    abstract fun bind(binding: ViewBinding)

    abstract fun getViewBinding(inflater: LayoutInflater): ViewBinding

}
