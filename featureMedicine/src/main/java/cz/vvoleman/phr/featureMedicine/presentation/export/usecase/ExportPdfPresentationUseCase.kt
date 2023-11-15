package cz.vvoleman.phr.featureMedicine.presentation.export.usecase

import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportMedicineSchedulePresentationModel

interface ExportPdfPresentationUseCase {

    suspend fun execute(exportData: List<ExportMedicineSchedulePresentationModel>): Boolean

}