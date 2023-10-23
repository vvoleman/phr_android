package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule

import androidx.room.Embedded
import androidx.room.TypeConverters
import cz.vvoleman.phr.featureMedicine.data.datasource.room.ListSubstanceAmountDataSourceConverter
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.SubstanceAmountDataSourceModel

data class ListSubstanceAmountDataSourceModel(
    val items: List<SubstanceAmountDataSourceModel>
)