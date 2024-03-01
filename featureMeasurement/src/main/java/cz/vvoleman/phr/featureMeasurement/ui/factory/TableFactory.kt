package cz.vvoleman.phr.featureMeasurement.ui.factory

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel
import cz.vvoleman.phr.featureMeasurement.ui.view.detail.groupie.ButtonItem
import cz.vvoleman.phr.featureMeasurement.ui.view.detail.groupie.ColumnContainer
import cz.vvoleman.phr.featureMeasurement.ui.view.detail.groupie.HeaderItem
import cz.vvoleman.phr.featureMeasurement.ui.view.detail.groupie.RowItem
import cz.vvoleman.phr.featureMeasurement.ui.view.detail.groupie.TableContainer

class TableFactory(
    private val entryTableInterface: EntryTableInterface
) {
    fun create(items: List<EntryInfoUiModel>) : TableContainer {
        if (items.isEmpty()) {
            return TableContainer(emptyList())
        }

        val headers = listOf("Datum").plus(items.first().fields.map { it.name }).plus("Akce").map { HeaderItem(it) }

        val columns = mutableMapOf<String,MutableList<BindableItem<*>>>()

        for(i in headers.indices){
            columns[i.toString()] = mutableListOf(headers[i])
        }

        val dates = items.map { it.entry.createdAt.toLocalString() }
        columns["0"]!!.addAll(dates.map { RowItem(it) })

        val fields = items.first().fields

        for(i in fields.indices){
            val values = items.map { it.entry.values[fields[i].id] ?: "" }
            columns[(i+1).toString()]!!.addAll(values.map { RowItem(it) })
        }

        val lastIndex = headers.size - 1
        val buttons = items.map {
            ButtonItem(it) { item, anchorView ->
                entryTableInterface.onItemOptionsMenuClicked(item, anchorView)
            }
        }
        columns[lastIndex.toString()]!!.addAll(buttons)

        val columnContainers = mutableListOf<ColumnContainer>()
        for(column in columns.values){
            columnContainers.add(ColumnContainer(column))
        }


        return TableContainer(columnContainers)
    }

    interface EntryTableInterface {
        fun onItemOptionsMenuClicked(item: EntryInfoUiModel, anchorView: View)
    }

}
