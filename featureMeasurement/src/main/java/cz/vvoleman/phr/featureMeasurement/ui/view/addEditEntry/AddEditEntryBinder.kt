package cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentAddEditEntryBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryViewState
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.EntryField
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.EntryFieldAdapter
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.fields.NumericEntryField
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.fields.TextEntryField

class AddEditEntryBinder() :
    BaseViewStateBinder<AddEditEntryViewState, FragmentAddEditEntryBinding, AddEditEntryBinder.Notification>(),
    EntryField.EntryFieldListener {

    private var items: List<EntryField> = listOf()

    override fun firstBind(viewBinding: FragmentAddEditEntryBinding, viewState: AddEditEntryViewState) {
        super.firstBind(viewBinding, viewState)

        items = listOf(
            NumericEntryField(lifecycleScope, "Hmotnost", 80, this),
            TextEntryField("Poznámka", "Nějaká poznámka")
        )

        val entryAdapter = EntryFieldAdapter(items)
        viewBinding.recyclerView.apply {
            adapter = entryAdapter
            layoutManager = LinearLayoutManager(viewBinding.root.context)
            setHasFixedSize(true)
        }

        viewBinding.buttonSave.setOnClickListener {
            Log.d("AddEditEntryBinder", "firstBind: ${items.map { it.getLabel() }}")
        }
    }

    sealed class Notification {
        data class EntryFieldValueChanged(val id: String, val value: String) : Notification()
    }

    override fun onValueChanged(id: String, value: String) {
        notify(Notification.EntryFieldValueChanged(id, value))
    }

}
