package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

class SelectFileDestination : PresentationDestination {

    object Cancel : PresentationDestination
    data class Success(val selectedOptions: SelectedOptionsPresentationModel) : PresentationDestination

}