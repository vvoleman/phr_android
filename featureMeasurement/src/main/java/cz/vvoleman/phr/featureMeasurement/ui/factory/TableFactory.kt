package cz.vvoleman.phr.featureMeasurement.ui.factory

import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel
import cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry.groupie.ColumnContainer
import cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry.groupie.HeaderItem
import cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry.groupie.RowItem
import cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry.groupie.TableContainer

class TableFactory {
    fun create(items: List<EntryInfoUiModel>) : TableContainer {
        val headers = listOf("Datum").plus(items.first().fields.map { it.name }).map { HeaderItem(it) }

        val columns = mutableMapOf<String,List<BindableItem<*>>>()

        // Dates
        val dates = items.map { it.entry.createdAt.toLocalString() }
        columns["Datum"] = dates.map { date -> RowItem(date) }

        // Fields
        items.first().fields.forEach { field ->
            val values = items.map { it.entry.values[field.id] ?: "" }
            columns[field.name] = values.map { value -> RowItem(value) }
        }

        items.first().fields.forEach { field ->
            val values = items.map { it.entry.values[field.id] ?: "" }
            columns[field.name] = values.map { value -> RowItem(value) }
        }

        val columnContainers = mutableListOf<ColumnContainer>()
        for(column in columns.values){
            columnContainers.add(ColumnContainer(column))
        }


        return TableContainer(columnContainers)
    }

}
