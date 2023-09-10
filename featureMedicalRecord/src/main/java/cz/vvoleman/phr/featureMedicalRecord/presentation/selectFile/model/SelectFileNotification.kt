package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model

sealed class SelectFileNotification {
    object OptionsRecognized : SelectFileNotification()
    object Error : SelectFileNotification()
}
