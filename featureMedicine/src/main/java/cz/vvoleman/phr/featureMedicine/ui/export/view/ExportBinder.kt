package cz.vvoleman.phr.featureMedicine.ui.export.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMedicine.databinding.FragmentExportBinding
import cz.vvoleman.phr.featureMedicine.domain.model.export.ExportType
import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportViewState
import cz.vvoleman.phr.featureMedicine.ui.export.adapter.ExportAdapter
import cz.vvoleman.phr.featureMedicine.ui.export.mapper.ExportUiModelToPresentationMapper
import kotlinx.coroutines.CoroutineScope

class ExportBinder(
    private val exportUiMapper: ExportUiModelToPresentationMapper,
) : BaseViewStateBinder<ExportViewState, FragmentExportBinding, ExportBinder.Notification>() {

    private lateinit var _adapter: ExportAdapter
    private var lastExportList = emptyList<String>()

    override fun init(viewBinding: FragmentExportBinding, context: Context, lifecycleScope: CoroutineScope) {
        super.init(viewBinding, context, lifecycleScope)

        viewBinding.recyclerViewPreview.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(16))
        }
    }

    override fun bind(viewBinding: FragmentExportBinding, viewState: ExportViewState) {
        if (viewBinding.editTextStartAt.text.toString() != viewState.dateRange.first.toLocalString()) {
            viewBinding.editTextStartAt.setText(viewState.dateRange.first.toLocalString())
        }

        if (viewBinding.editTextEndAt.text.toString() != viewState.dateRange.second.toLocalString()) {
            viewBinding.editTextEndAt.setText(viewState.dateRange.second.toLocalString())
        }

        Log.d(TAG, "bind: ${viewState.exportData.size}")

        val exportIds = viewState.exportData.map { it.id }
        if (exportIds != lastExportList) {
            lastExportList = exportIds
            val newList = viewState.exportData.map { exportUiMapper.toUi(it) }.flatten().sortedBy { it.dateTime }
            (viewBinding.recyclerViewPreview.adapter as ExportAdapter).submitList(newList)
        }
    }

    fun setAdapter(adapter: ExportAdapter) {
        _adapter = adapter
    }

    sealed class Notification

    companion object {
        const val TAG = "ExportBinder"
    }

}