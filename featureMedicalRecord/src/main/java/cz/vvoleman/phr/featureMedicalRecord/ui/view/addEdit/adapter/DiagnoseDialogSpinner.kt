package cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter

import android.R
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.featureMedicalRecord.databinding.CustomDiagnoseSpinnerBinding
import cz.vvoleman.phr.featureMedicalRecord.databinding.DialogDiagnoseSpinnerBinding
import cz.vvoleman.phr.featureMedicalRecord.ui.model.DiagnoseItemUiModel

class DiagnoseDialogSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),
    View.OnTouchListener,
    DiagnoseAdapter.OnItemClickListener {

    private var spinner: Spinner
    private var dialogListener: OnDialogListener? = null
    private var dialog: AlertDialog
    private var _binding: DialogDiagnoseSpinnerBinding? = null
    private val binding get() = _binding!!

    private var recyclerViewAdapter: DiagnoseAdapter

    init {
        val layoutBinding =
            CustomDiagnoseSpinnerBinding.inflate(LayoutInflater.from(context), this, true)
        _binding = DialogDiagnoseSpinnerBinding.inflate(
            LayoutInflater.from(layoutBinding.root.context),
            layoutBinding.root,
            false
        )

        spinner = layoutBinding.spinner
        spinner.setOnTouchListener(this)

        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
//        builder.setTitle("Custom Dialog")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            // handle the selection here
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        dialog = builder.create()

        recyclerViewAdapter = DiagnoseAdapter(this)
//        recyclerViewAdapter.addLoadStateListener {loadState ->
//            binding.apply {
//                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
//                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
//                textViewError.isVisible = loadState.source.refresh is LoadState.Error
//
//                // Empty view
//                if (loadState.source.refresh is LoadState.NotLoading &&
//                    loadState.append.endOfPaginationReached && recyclerViewAdapter.itemCount < 1) {
//                    textViewEmpty.isVisible = true
//                    recyclerViewDialogSpinner.isVisible = true
//                } else {
//                    textViewEmpty.isVisible = false
//                }
//            }
//        }

        // In dialog layout, we have a recyclerView with a list of items
        // We need to set the adapter for the recyclerView here
        // We can also set the layout manager here
        binding.apply {
            recyclerViewDialogSpinner.apply {
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
                setHasFixedSize(true)
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("SearchView", "onQueryTextChange: $newText")
                    dialogListener?.onDiagnoseSearch(newText ?: "")
                    return false
                }
            })
        }
    }

    suspend fun setData(data: List<DiagnoseItemUiModel>) {
        recyclerViewAdapter.submitList(data)
    }

    fun setSelectedItem(selected: DiagnoseItemUiModel) {
        val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, listOf(selected))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val position = 0

        if (position != -1) {
            spinner.setSelection(position)
        }
    }

    fun setListener(listener: OnDialogListener) {
        dialogListener = listener
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        Log.d(TAG, event?.action.toString())

        if (event?.action == MotionEvent.ACTION_UP) {
            dialog.show()
        }

        return true
    }

    override fun onItemClicked(item: DiagnoseItemUiModel, position: Int): Boolean {
        val listenerResult = dialogListener?.onDiagnoseClicked(item)
        dialog.dismiss()
        setSelectedItem(item)

        return listenerResult ?: false
    }

    interface OnDialogListener {
        fun onDiagnoseClicked(item: DiagnoseItemUiModel): Boolean
        fun onDiagnoseSearch(query: String)
    }

    companion object {
        private const val TAG = "DiagnoseDialogSpinner"
    }
}
