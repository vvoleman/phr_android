package cz.vvoleman.phr.feature_medicalrecord.ui.mapper

import android.util.Log
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsDestination

class ListMedicalRecordsDestinationUiMapper : DestinationUiMapper {
    override fun navigate(destination: PresentationDestination) {
        when (destination) {
            is ListMedicalRecordsDestination.NewMedicalRecord -> {
                Log.d("ListMedicalRecordsDestinationUiMapper", "NewMedicalRecord")
            }
            is ListMedicalRecordsDestination.EditMedicalRecord -> {
                Log.d("ListMedicalRecordsDestinationUiMapper", "EditMedicalRecord")
            }
        }
    }
}