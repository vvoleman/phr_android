package cz.vvoleman.phr.common.ui.model

import java.io.Serializable

data class GroupedItemsUiModel<ITEM : Any>(
    val value: Any,
    val items: List<ITEM>
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
