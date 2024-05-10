package cz.vvoleman.phr.common.ui.view.healthcare.detailMedicalWorker.groupie

import android.view.View
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.ui.model.healthcare.core.SpecificMedicalWorkerUiModel
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.ItemDetailMedicalWorkerBinding

class SpecificWorkerContainer(
    private val specificWorker: SpecificMedicalWorkerUiModel,
    private val items: List<BindableItem<*>>
) : BindableItem<ItemDetailMedicalWorkerBinding>() {

    override fun bind(viewBinding: ItemDetailMedicalWorkerBinding, position: Int) {
        viewBinding.textViewFacilityName.text = specificWorker.medicalWorker?.name

        val groupieAdapter = GroupieAdapter().apply {
            addAll(items)
        }

        viewBinding.recyclerView.apply {
            adapter = groupieAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                viewBinding.root.context,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
        }
    }

    override fun getLayout() = R.layout.item_detail_medical_worker

    override fun initializeViewBinding(view: View): ItemDetailMedicalWorkerBinding {
        return ItemDetailMedicalWorkerBinding.bind(view)
    }
}
