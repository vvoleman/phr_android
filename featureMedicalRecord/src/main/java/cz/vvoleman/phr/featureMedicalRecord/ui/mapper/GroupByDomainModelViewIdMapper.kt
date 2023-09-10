package cz.vvoleman.phr.featureMedicalRecord.ui.mapper

import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.domain.model.list.GroupByDomainModel

class GroupByDomainModelViewIdMapper {

    fun toViewId(groupBy: GroupByDomainModel): Int {
        return when (groupBy) {
            GroupByDomainModel.DATE -> R.id.radio_by_date
            GroupByDomainModel.PROBLEM_CATEGORY -> R.id.radio_by_problem_category
            GroupByDomainModel.MEDICAL_WORKER -> R.id.radio_by_medical_worker
        }
    }

    fun toDomainModel(viewId: Int): GroupByDomainModel {
        return when (viewId) {
            R.id.radio_by_date -> GroupByDomainModel.DATE
            R.id.radio_by_problem_category -> GroupByDomainModel.PROBLEM_CATEGORY
            R.id.radio_by_medical_worker -> GroupByDomainModel.MEDICAL_WORKER
            else -> throw IllegalArgumentException("Unknown view id: $viewId")
        }
    }
}
