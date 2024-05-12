package cz.vvoleman.phr.common.ui.mapper.healthcare.destination

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker.DetailMedicalWorkerDestination
import cz.vvoleman.phr.common.ui.view.healthcare.detailMedicalWorker.DetailMedicalWorkerFragmentDirections

class DetailMedicalWorkerDestinationUiMapper(
    navManager: NavManager
) : DestinationUiMapper(navManager) {

    override fun navigate(destination: PresentationDestination) {
        when (val dest = destination as DetailMedicalWorkerDestination) {
            is DetailMedicalWorkerDestination.Edit -> {
                val action =
                    DetailMedicalWorkerFragmentDirections
                        .actionDetailMedicalWorkerFragmentToAddEditMedicalWorkerFragment(
                            dest.medicalWorkerId
                        )

                navManager.navigate(action)
            }
        }
    }
}
