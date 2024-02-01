package cz.vvoleman.phr.featureMeasurement.ui.component.fieldInfoTable

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cz.vvoleman.phr.featureMeasurement.databinding.ViewFieldInfoTableBinding

class FieldInfoTable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewFieldInfoTableBinding
//    private val _adapter: FieldInfoTableAdapter

    init {
        binding = ViewFieldInfoTableBinding.inflate(LayoutInflater.from(context), this, true)
    }

}
