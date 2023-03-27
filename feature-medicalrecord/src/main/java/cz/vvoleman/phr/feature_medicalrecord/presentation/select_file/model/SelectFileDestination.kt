package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import android.net.Uri
import cz.vvoleman.phr.base.presentation.model.PresentationDestination

class SelectFileDestination : PresentationDestination {

    object Cancel : PresentationDestination
    data class SuccessWithOptions(val selectedOptions: SelectedOptionsPresentationModel, val fileUri: Uri) : PresentationDestination
    data class Success(val fileUri: Uri) : PresentationDestination

}