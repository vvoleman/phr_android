package cz.vvoleman.featureMeasurement.domain.repository

import cz.vvoleman.featureMeasurement.domain.model.core.field.unit.UnitGroupDomainModel

interface GetUnitGroupsRepository {

    suspend fun getUnitGroups(): List<UnitGroupDomainModel>

}
