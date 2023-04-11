package cz.vvoleman.phr.feature_medicine.ui.medicine_selector

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.vvoleman.phr.feature_medicine.R
import cz.vvoleman.phr.feature_medicine.databinding.DialogMedicineSelectorBinding
import cz.vvoleman.phr.feature_medicine.databinding.ItemMedicineSelectorBinding
import cz.vvoleman.phr.feature_medicine.databinding.ViewMedicineSelectorBinding
import cz.vvoleman.phr.feature_medicine.domain.model.medicine.MedicineDomainModel
import cz.vvoleman.phr.feature_medicine.ui.model.list.MedicineUiModel

class MedicineSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), MedicineSelectorAdapter.OnItemClickListener {

    private var recyclerViewAdapter: MedicineSelectorAdapter
    private val dialog: MaterialAlertDialogBuilder
    private var layoutNoMedicine: LinearLayout
    private var layoutMedicine: LinearLayout
    private var listener: MedicineSelectorListener? = null
    private var medicine: MedicineUiModel? = null

    private val dialogBinding: DialogMedicineSelectorBinding

    init {
        val layoutBinding =
            ViewMedicineSelectorBinding.inflate(LayoutInflater.from(context), this, true)

        layoutNoMedicine = layoutBinding.layoutNoMedicine
        layoutMedicine = layoutBinding.layoutMedicine

        dialog = MaterialAlertDialogBuilder(context)
        dialogBinding = DialogMedicineSelectorBinding.inflate(LayoutInflater.from(context), this, false)
        dialog.setView(dialogBinding.root)
        dialog.setPositiveButton("OK") { _, _ ->
            listener?.onMedicineSelected(medicine)
        }
        dialog.setNegativeButton("Cancel") { _, _ ->
            Log.d("MedicineSelector", "Cancel")
        }

        layoutNoMedicine.setOnClickListener {
            dialog.show()
        }

        recyclerViewAdapter = MedicineSelectorAdapter(this)
        recyclerViewAdapter.addLoadStateListener { loadState ->
            dialogBinding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewMedicines.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }

        dialogBinding.apply {
            recyclerViewMedicines.apply {
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(this.context)
                setHasFixedSize(false)
            }
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    listener?.onMedicineSelectorSearch(newText!!)
                    return false
                }

            })
        }
    }

    suspend fun setData(data: List<MedicineUiModel>) {
        recyclerViewAdapter.submitData(PagingData.from(data))
    }

    fun setListener(listener: MedicineSelectorListener) {
        this.listener = listener
    }

    interface MedicineSelectorListener {
        fun onMedicineSelected(medicine: MedicineUiModel?)
        fun onMedicineSelectorSearch(query: String)
    }

    override fun onItemClick(item: MedicineUiModel) {
        medicine = item
    }

}