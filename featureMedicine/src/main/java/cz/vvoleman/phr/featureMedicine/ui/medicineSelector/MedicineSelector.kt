package cz.vvoleman.phr.featureMedicine.ui.medicineSelector

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.vvoleman.phr.featureMedicine.databinding.DialogMedicineSelectorBinding
import cz.vvoleman.phr.featureMedicine.databinding.ViewMedicineSelectorBinding
import cz.vvoleman.phr.featureMedicine.ui.model.list.MedicineUiModel

@Suppress("Indentation")
class MedicineSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), MedicineSelectorAdapter.OnItemClickListener {

    private val binding: ViewMedicineSelectorBinding
    private var recyclerViewAdapter: MedicineSelectorAdapter
    private val dialog: AlertDialog
    private var layoutNoMedicine: LinearLayout
    private var layoutMedicine: LinearLayout
    private var listener: MedicineSelectorListener? = null
    private var medicine: MedicineUiModel? = null

    private val dialogBinding: DialogMedicineSelectorBinding

    init {
        binding =
            ViewMedicineSelectorBinding.inflate(LayoutInflater.from(context), this, true)

        layoutNoMedicine = binding.layoutNoMedicine
        layoutMedicine = binding.layoutMedicine

        dialogBinding = DialogMedicineSelectorBinding.inflate(LayoutInflater.from(context))

        val builder = MaterialAlertDialogBuilder(context)
        builder.setView(dialogBinding.root)
        builder.setPositiveButton("OK") { _, _ ->
            listener?.onMedicineSelected(medicine)
            setupMedicine()
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            Log.d("MedicineSelector", "Cancel")
        }

        dialog = builder.create()

        binding.root.setOnClickListener {
            listener?.onMedicineSelectorSearch("")
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
            searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener,
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

    private fun setupMedicine() {
        binding.layoutNoMedicine.isVisible = false

        binding.textViewMedicine.text = medicine!!.name
        binding.textViewMedicineDosage.text = medicine!!.packaging.packaging
        binding.textViewMedicineForm.text = medicine!!.packaging.form.name
        binding.textViewMedicineSize.text = medicine!!.packaging.packaging

        binding.layoutMedicine.isVisible = true
    }

    public fun setSelectedMedicine(medicine: MedicineUiModel?) {
        this.medicine = medicine
        if (medicine != null) {
            setupMedicine()
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
