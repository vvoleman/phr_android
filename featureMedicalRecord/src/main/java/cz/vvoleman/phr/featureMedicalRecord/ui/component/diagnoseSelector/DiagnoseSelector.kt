package cz.vvoleman.phr.featureMedicalRecord.ui.component.diagnoseSelector

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingData
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.featureMedicalRecord.databinding.DialogDiagnoseSelectorBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.ViewDiagnoseSelectorBinding
import cz.vvoleman.phr.featureMedicalRecord.ui.adapter.DiagnoseSelectorAdapter
import cz.vvoleman.phr.featureMedicalRecord.ui.model.DiagnoseUiModel

class DiagnoseSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), DiagnoseSelectorAdapter.DiagnoseSelectorAdapterListener {

    private val position: Int? = null
    private val binding: ViewDiagnoseSelectorBinding
    private val dialogBinding: DialogDiagnoseSelectorBinding
    private val dialog: AlertDialog
    private var recyclerViewAdapter: DiagnoseSelectorAdapter

    private var isFirstOpen = false
    private var listener: DiagnoseSelectorListener? = null
    private var diagnose: DiagnoseUiModel? = null

    init {
        binding = ViewDiagnoseSelectorBinding.inflate(LayoutInflater.from(context), this, true)

        dialogBinding = DialogDiagnoseSelectorBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(context)
        builder.setView(dialogBinding.root)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { _, _ ->
            setupDiagnose()
            listener?.onDiagnoseSelected(diagnose, position)
        }
        builder.setNegativeButton("Cancel") { _, _ -> }

        dialog = builder.create()

        binding.textInputEditTextName.setOnClickListener {
            if (!isFirstOpen) {
                isFirstOpen = true
                listener?.onDiagnoseSelectorSearch("") { pagingData ->
                    setData(pagingData)
                }
            }
            dialog.show()
        }

        recyclerViewAdapter = DiagnoseSelectorAdapter(this)
        recyclerViewAdapter.addLoadStateListener { loadState ->
            dialogBinding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            dialogBinding.textViewNoResults.isVisible = loadState.append.endOfPaginationReached && recyclerViewAdapter.itemCount < 1
            dialogBinding.textViewError.isVisible = loadState.refresh is LoadState.Error
        }

        dialogBinding.recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(false)
        }

        dialogBinding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                diagnose = null
                recyclerViewAdapter.resetSelectedPosition()
                listener?.onDiagnoseSelectorSearch(query ?: "") {
                    setData(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    suspend fun setData(data: PagingData<DiagnoseUiModel>) {
        recyclerViewAdapter.submitData(data)
    }

    fun setListener(listener: DiagnoseSelectorListener) {
        this.listener = listener
    }

    fun setSelected(diagnose: DiagnoseUiModel?) {
        this.diagnose = diagnose
        setupDiagnose()
    }

    private fun setupDiagnose() {
        binding.textInputEditTextName.setText(diagnose?.name)
    }

    interface DiagnoseSelectorListener {
        fun onDiagnoseSelected(diagnose: DiagnoseUiModel?, position: Int?)
        fun onDiagnoseSelectorSearch(query: String, callback: suspend (PagingData<DiagnoseUiModel>) -> Unit)
    }

    override fun onDiagnoseSelected(diagnose: DiagnoseUiModel?) {
        this.diagnose = diagnose
    }

}
