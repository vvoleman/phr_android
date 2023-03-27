package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

sealed class SelectFileNotification {
    object OptionsRecognized : SelectFileNotification()
    object Error : SelectFileNotification()
}
