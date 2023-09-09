package cz.vvoleman.phr.feature_medicalrecord.data.mapper

import cz.vvoleman.phr.common.data.datasource.model.AddressDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.AddressDomainModel

class AddressDataSourceToDomainMapper {

    fun toDomain(address: AddressDataSourceModel): AddressDomainModel {
        return AddressDomainModel(
            city = address.city,
            street = address.street,
            houseNumber = address.house_number,
            zipCode = address.zip_code
        )
    }
}
