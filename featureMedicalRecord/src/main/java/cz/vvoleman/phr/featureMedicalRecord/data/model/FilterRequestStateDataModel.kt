package cz.vvoleman.phr.featureMedicalRecord.data.model

sealed class FilterRequestStateDataModel {

    object Empty : FilterRequestStateDataModel()
    object Category : FilterRequestStateDataModel()
    object Worker : FilterRequestStateDataModel()
    object CategoryAndWorker : FilterRequestStateDataModel()
}
