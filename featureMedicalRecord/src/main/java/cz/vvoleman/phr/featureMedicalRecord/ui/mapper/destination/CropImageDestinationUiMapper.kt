package cz.vvoleman.phr.featureMedicalRecord.ui.mapper.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage.CropImageDestination
import cz.vvoleman.phr.featureMedicalRecord.ui.view.cropImage.CropImageFragmentDirections

class CropImageDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as CropImageDestination) {
            is CropImageDestination.CropImage -> {
                val action = CropImageFragmentDirections.actionCropImageFragmentToSelectFileFragment(dest.parentViewState, dest.result)
                navManager.navigate(action)
            }

            CropImageDestination.Cancel -> TODO()
        }
    }
}
