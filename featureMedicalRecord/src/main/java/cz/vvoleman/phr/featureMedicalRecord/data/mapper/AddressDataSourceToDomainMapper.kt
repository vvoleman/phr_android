package cz.vvoleman.phr.featureMedicalRecord.data.mapper

import cz.vvoleman.phr.common.data.datasource.model.AddressDataSourceModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.AddressDomainModel

class AddressDataSourceToDomainMapper {

    fun toDomain(address: AddressDataSourceModel): AddressDomainModel {
        return AddressDomainModel(
            city = address.city,
            street = address.street,
            houseNumber = address.houseNumber,
            zipCode = address.zipCode
        )
    }
}
