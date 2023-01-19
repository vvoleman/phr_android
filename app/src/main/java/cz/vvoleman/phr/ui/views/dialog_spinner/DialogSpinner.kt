package cz.vvoleman.phr.ui.views.dialog_spinner

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.databinding.CustomDialogSpinnerDialogBinding
import cz.vvoleman.phr.databinding.CustomDialogSpinnerLayoutBinding

class DialogSpinner<T : Any> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),
    View.OnTouchListener,
    DialogOptionsAdapter.OnItemClickListener {

    private var spinner: Spinner
    private var dialogListener: DialogSpinnerListener? = null
    private var dialog: AlertDialog
    private var _binding: CustomDialogSpinnerDialogBinding? = null
    private val binding get() = _binding!!

    private var recyclerViewAdapter: DialogOptionsAdapter

    init {
        val layoutBinding =
            CustomDialogSpinnerLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        _binding = CustomDialogSpinnerDialogBinding.inflate(
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

        recyclerViewAdapter = DialogOptionsAdapter(this)

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
                    dialogListener?.onSearch(newText?: "")
                    return false
                }
            })
        }

    }

    fun setData(data: List<AdapterPair>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val mutableList = data.toMutableList()
        recyclerViewAdapter.submitList(mutableList)
        Log.d("DialogSpinner", "Number of items in list: ${data.size}")
        Log.d("DialogSpinner", "Number of items in adapter: ${recyclerViewAdapter.itemCount}")
    }

    fun setSelectedItem(selected: AdapterPair) {
        val adapter = spinner.adapter as ArrayAdapter<AdapterPair>
        val position = adapter.getPosition(selected)

        if (position != -1) {
            spinner.setSelection(position)
        }
    }

    fun setListener(listener: DialogSpinnerListener) {
        dialogListener = listener
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        Log.d("DialogSpinner", event?.action.toString())

        if (event?.action == MotionEvent.ACTION_UP) {
            dialog.show()
        }

        return true
    }

    override fun onItemClicked(item: AdapterPair, position: Int): Boolean {
        val listenerResult = dialogListener?.onItemSelected(item)
        dialog.dismiss()
        Log.d("DialogSpinner", "Listener result: $listenerResult")
        spinner.setSelection(position)

        return listenerResult ?: false
    }

    interface DialogSpinnerListener {
        fun onItemSelected(item: AdapterPair): Boolean
        fun onSearch(query: String)
    }

}