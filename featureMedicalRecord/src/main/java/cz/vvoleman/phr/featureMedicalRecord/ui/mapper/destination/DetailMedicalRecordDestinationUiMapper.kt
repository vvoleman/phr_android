package cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordDestination
import cz.vvoleman.phr.featureMedicalRecord.ui.view.detail.DetailMedicalRecordFragmentDirections

class DetailMedicalRecordDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as DetailMedicalRecordDestination) {
            is DetailMedicalRecordDestination.OpenGallery -> {
                val action = DetailMedicalRecordFragmentDirections
                    .actionDetailMedicalRecordFragmentToDetailGalleryFragment(
                        medicalRecordId = dest.medicalRecordId,
                        assetId = dest.assetId
                    )
                navManager.navigate(action)
            }
        }
    }
}
