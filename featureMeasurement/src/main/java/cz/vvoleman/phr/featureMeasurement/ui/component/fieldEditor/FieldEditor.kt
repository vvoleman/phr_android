package cz.vvoleman.phr.featureMeasurement.ui.component.fieldEditor

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.featureMeasurement.databinding.ViewFieldEditorBinding
import cz.vvoleman.phr.featureMeasurement.ui.component.fieldEditor.dialog.FieldEditorDialog
import cz.vvoleman.phr.featureMeasurement.ui.component.fieldEditor.dialog.NumericFieldEditorDialog
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupFieldUi
import cz.vvoleman.phr.featureMeasurement.ui.model.core.field.NumericFieldUiModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.field.unit.UnitGroupUiModel
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants

class FieldEditor @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), FieldAdapter.FieldAdapterListener,
    FieldEditorDialog.FieldEditorDialogListener {

    private val binding: ViewFieldEditorBinding
    private val _adapter: FieldAdapter

    private var _listener: FieldEditorListener? = null

    private var _unitGroups = emptyList<UnitGroupUiModel>()

    init {
        binding = ViewFieldEditorBinding.inflate(LayoutInflater.from(context), this, true)
        _adapter = FieldAdapter(this)

        binding.recyclerView.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(false)
        }

        binding.buttonAddField.setOnClickListener {
            Log.d("FieldEditor", "addField")
            _listener?.onAddField(NumericFieldUiModel(System.currentTimeMillis().toString(), ""))
        }
    }

    fun setListener(listener: FieldEditorListener) {
        _listener = listener
    }

    fun setItems(items: List<MeasurementGroupFieldUi>) {
        _adapter.submitList(items)
    }

    override fun onFieldClick(item: MeasurementGroupFieldUi, position: Int) {
        _listener?.onFieldClick(item, position)
    }

    override fun onFieldOptionsMenuClicked(item: MeasurementGroupFieldUi, view: View) {
        _listener?.onFieldOptionsMenuClicked(item, view)
    }

    fun startEdit(item: MeasurementGroupFieldUi) {
        openDialog(item)
    }

    interface FieldEditorListener : FieldAdapter.FieldAdapterListener {
        fun onStartDialog(dialog: DialogFragment, item: MeasurementGroupFieldUi)
        fun onAddField(item: MeasurementGroupFieldUi)
        fun onSave(item: MeasurementGroupFieldUi)
    }

    private fun openDialog(item: MeasurementGroupFieldUi) {
        when(item) {
            is NumericFieldUiModel -> {
                val dialog = NumericFieldEditorDialog(_unitGroups,this, item)
                _listener?.onStartDialog(dialog, item)
            }
            else -> {
                Log.e("FieldEditor", "Unable to open dialog for item '$item'")
            }
        }
    }

    override fun onDialogSave(data: MeasurementGroupFieldUi) {
        Log.d("FieldEditor", "onDialogSave: $data")
        _listener?.onSave(data)
    }

    fun setUnitGroups(groups: List<UnitGroupUiModel>) {
        _unitGroups = groups
    }

}
