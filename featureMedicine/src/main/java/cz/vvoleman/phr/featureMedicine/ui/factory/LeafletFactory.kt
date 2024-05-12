package cz.vvoleman.phr.featureMedicine.ui.factory

import cz.vvoleman.phr.featureMedicine.ui.list.model.MedicineUiModel

object LeafletFactory {

    fun getLeafletLink(item: MedicineUiModel): String {
        return "https://phr.vvoleman.eu/api/medical-product/document?id=${item.id}"
    }
}
