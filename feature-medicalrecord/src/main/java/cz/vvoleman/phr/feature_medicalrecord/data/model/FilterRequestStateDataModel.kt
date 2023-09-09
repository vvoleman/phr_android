package cz.vvoleman.phr.feature_medicalrecord.data.model

sealed class FilterRequestStateDataModel {

    object Empty : FilterRequestStateDataModel()
    object Category : FilterRequestStateDataModel()
    object Worker : FilterRequestStateDataModel()
    object CategoryAndWorker : FilterRequestStateDataModel()
}
