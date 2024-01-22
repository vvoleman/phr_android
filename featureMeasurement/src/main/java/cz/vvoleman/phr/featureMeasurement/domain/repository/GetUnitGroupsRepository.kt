package cz.vvoleman.phr.featureMeasurement.domain.repository

import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.unit.UnitGroupDomainModel

interface GetUnitGroupsRepository {

    suspend fun getUnitGroups(): List<UnitGroupDomainModel>

}
