package cz.vvoleman.phr.featureMedicine.ui.list.fragment.viewModel

import androidx.lifecycle.ViewModel
import cz.vvoleman.phr.common.ui.model.GroupedItemsUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.adapter.MedicineCatalogueAdapter
import cz.vvoleman.phr.featureMedicine.ui.list.model.schedule.MedicineScheduleUiModel

class MedicineCatalogueViewModel : ViewModel() {

    private var listener: MedicineCatalogueAdapter.MedicineCatalogueAdapterInterface? = null

    private var items: List<GroupedItemsUiModel<MedicineScheduleUiModel>> = emptyList()

    val isReady: Boolean
        get() = items.isNotEmpty() && listener != null

    fun setItems(allSchedules: List<GroupedItemsUiModel<MedicineScheduleUiModel>>) {
        this.items = allSchedules
    }

    fun getItems(): List<GroupedItemsUiModel<MedicineScheduleUiModel>> {
        return items
    }

    fun setListener(listener: MedicineCatalogueAdapter.MedicineCatalogueAdapterInterface) {
        this.listener = listener
    }

    fun getListener(): MedicineCatalogueAdapter.MedicineCatalogueAdapterInterface? {
        return listener
    }


}