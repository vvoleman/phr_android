package cz.vvoleman.phr.common.ui.component.facilitySelector

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalFacilityUiModel
import cz.vvoleman.phr.common.utils.SizingConstants.MARGIN_SIZE
import cz.vvoleman.phr.common_datasource.databinding.DialogFacilitySelectorBinding
import cz.vvoleman.phr.common_datasource.databinding.ViewFacilitySelectorBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FacilitySelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), FacilitySelectorAdapter.FacilitySelectorAdapterListener {

    private var position: Int? = null
    private val binding: ViewFacilitySelectorBinding
    private val dialogBinding: DialogFacilitySelectorBinding
    private val dialog: AlertDialog
    private var recyclerViewAdapter: FacilitySelectorAdapter

    private var listener: FacilitySelectorListener? = null
    private var facility: MedicalFacilityUiModel? = null

    init {
        binding = ViewFacilitySelectorBinding.inflate(LayoutInflater.from(context), this, true)

        dialogBinding = DialogFacilitySelectorBinding.inflate(LayoutInflater.from(context))

        val builder = MaterialAlertDialogBuilder(context)
        builder.setView(dialogBinding.root)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { _, _ ->
            setupFacility()
            listener?.onFacilitySelected(facility, position)
        }
        builder.setNegativeButton("Cancel") { _, _ -> }

        dialog = builder.create()

        binding.textInputEditTextName.setOnClickListener {
            dialog.show()
        }

        recyclerViewAdapter = FacilitySelectorAdapter(this)
        recyclerViewAdapter.addLoadStateListener { loadState ->
            dialogBinding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            dialogBinding.textViewNoResults.isVisible = loadState.append.endOfPaginationReached && recyclerViewAdapter.itemCount < 1
            dialogBinding.textViewError.isVisible = loadState.refresh is LoadState.Error
        }

        dialogBinding.recyclerViewFacilities.apply {
            adapter = recyclerViewAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(MARGIN_SIZE))
            setHasFixedSize(false)
        }

        dialogBinding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                facility = null
                recyclerViewAdapter.resetSelectedPosition()
                listener?.onFacilitySelectorSearch(query ?: "") {
                    setData(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupFacility() {
        binding.textInputEditTextName.setText(facility?.fullName ?: "")
    }

    fun setSelected(facility: MedicalFacilityUiModel?) {
        this.facility = facility
        setupFacility()
    }

    fun setListener(listener: FacilitySelectorListener) {
        this.listener = listener

        listener.onFacilitySelectorSearch("") { pagingData ->
            setData(pagingData)
        }
    }

    fun setError(error: String?) {
        binding.textInputLayoutName.error = error
    }

    suspend fun setData(data: PagingData<MedicalFacilityUiModel>) {
        recyclerViewAdapter.submitData(data)
    }

    interface FacilitySelectorListener {
        fun onFacilitySelected(facility: MedicalFacilityUiModel?, position: Int? = null)
        fun onFacilitySelectorSearch(query: String, callback: suspend (PagingData<MedicalFacilityUiModel>) -> Unit)
    }

    override fun onFacilitySelected(item: MedicalFacilityUiModel?) {
        facility = item
    }

    fun setPosition(position: Int) {
        this.position = position
    }
}
