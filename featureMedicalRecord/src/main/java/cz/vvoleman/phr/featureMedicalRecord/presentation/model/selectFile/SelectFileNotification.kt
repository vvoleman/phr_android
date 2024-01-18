package cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile

sealed class SelectFileNotification {
    object OptionsRecognized : SelectFileNotification()
    object Error : SelectFileNotification()
}
