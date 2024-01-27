package cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentAddEditEntryBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryViewState
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.EntryFieldAdapter
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.EntryFieldItem
import cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry.EntryFieldUiModelToEntryFieldItemMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry.EntryFieldUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.core.MeasurementGroupFieldUiModelToPresentationMapper

class AddEditEntryBinder(
    private val fieldMapper: MeasurementGroupFieldUiModelToPresentationMapper,
    private val entryFieldMapper: EntryFieldUiModelToPresentationMapper,
    private val entryFieldItemMapper: EntryFieldUiModelToEntryFieldItemMapper
) :
    BaseViewStateBinder<AddEditEntryViewState, FragmentAddEditEntryBinding, AddEditEntryBinder.Notification>(),
    EntryFieldItem.EntryFieldItemListener {

    private var items: List<EntryFieldItem> = listOf()

    override fun firstBind(viewBinding: FragmentAddEditEntryBinding, viewState: AddEditEntryViewState) {
        super.firstBind(viewBinding, viewState)

        items = entryFieldItemMapper.toEntryFieldItem(
            entryFields = entryFieldMapper.toUi(viewState.entryFields.values.toList()),
            fields = fieldMapper.toUi(viewState.measurementGroup.fields),
            listener = this,
            coroutineScope = lifecycleScope
        )

        val entryAdapter = EntryFieldAdapter(items)
        viewBinding.recyclerView.apply {
            adapter = entryAdapter
            layoutManager = LinearLayoutManager(viewBinding.root.context)
            setHasFixedSize(true)
        }

        viewBinding.buttonSave.setOnClickListener {
            notify(Notification.Submit)
        }

        viewState.dateTime?.toLocalDate()?.let { viewBinding.datePicker.setDate(it) }
        viewState.dateTime?.toLocalTime()?.let { viewBinding.timePicker.setTime(it) }
    }

    override fun bind(viewBinding: FragmentAddEditEntryBinding, viewState: AddEditEntryViewState) {
        super.bind(viewBinding, viewState)

        val canSubmit = canSubmit(viewState)
        viewBinding.buttonSave.isEnabled = canSubmit
    }

    sealed class Notification {
        data class EntryFieldValueChanged(val id: String, val value: String) : Notification()
        object Submit : Notification()
    }

    override fun onValueChanged(id: String, value: String) {
        notify(Notification.EntryFieldValueChanged(id, value))
    }

    private fun canSubmit(viewState: AddEditEntryViewState): Boolean {
        val values = viewState.entryFields
        for (item in items) {
            val field = values[item.id]
            if (field == null) {
                Log.e("AddEditEntryBinder", "submitOnValid: Field with id ${item.id} not found")
                continue
            }

            val status = item.validate(field.value)
            if (status == EntryFieldItem.ItemStatus.Invalid) {
                return false
            }
        }

        return true
    }

}
