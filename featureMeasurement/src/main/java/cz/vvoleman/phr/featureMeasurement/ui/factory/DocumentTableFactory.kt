package cz.vvoleman.phr.featureMeasurement.ui.factory

import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.utils.DateTimePattern
import cz.vvoleman.phr.common.utils.toPattern
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel
import cz.vvoleman.phr.featureMeasurement.ui.view.export.groupie.ExportColumnContainer
import cz.vvoleman.phr.featureMeasurement.ui.view.export.groupie.ExportHeaderItem
import cz.vvoleman.phr.featureMeasurement.ui.view.export.groupie.ExportRowItem
import cz.vvoleman.phr.featureMeasurement.ui.view.export.groupie.ExportTableContainer

class DocumentTableFactory {

    fun create(items: List<EntryInfoUiModel>) : ExportTableContainer {
        if (items.isEmpty()) {
            return ExportTableContainer(emptyList())
        }

        val headers = listOf("Datum").plus(items.first().fields.map { it.name }).map { ExportHeaderItem(it) }

        val columns = mutableMapOf<String,MutableList<BindableItem<*>>>()

        for(i in headers.indices){
            columns[i.toString()] = mutableListOf(headers[i])
        }

        val dates = items.map { it.entry.createdAt.toPattern(DateTimePattern.DATE_TIME) }
        columns["0"]!!.addAll(dates.map { ExportRowItem(it) })

        val fields = items.first().fields

        for(i in fields.indices){
            val values = items.map {
                it.entry.values[fields[i].id] ?: ""
            }
            columns[(i+1).toString()]!!.addAll(values.map { ExportRowItem(it) })
        }

        val columnContainers = mutableListOf<ExportColumnContainer>()
        for(column in columns.values){
            columnContainers.add(ExportColumnContainer(column))
        }

        return ExportTableContainer(columnContainers)
    }

}
