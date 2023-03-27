package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

sealed class SelectFileNotification {
    data class OptionsRecognized(val recognizedOptions: RecognizedOptionsPresentationModel) : SelectFileNotification()
    object SelectOptions : SelectFileNotification()
    object Error : SelectFileNotification()
}
