package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file

import android.util.Log
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.AddMedicalRecordAssetDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SaveFileRequestDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.CreateMedicalRecordAssetRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file.SaveFileRepository

class SaveMedicalRecordFileUseCase(
    private val createMedicalRecordAssetRepository: CreateMedicalRecordAssetRepository,
    private val saveFileRepository: SaveFileRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SaveFileRequestDomainModel, Boolean>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SaveFileRequestDomainModel): Boolean {
        val filePath = saveFileRepository.saveFile(request.uri)
        Log.d("SaveMedicalRecordFileUseCase", "File saved to $filePath")

        val asset = AddMedicalRecordAssetDomainModel(
            medicalRecordId = request.medicalRecordId,
            url = filePath
        )

        val assetId = createMedicalRecordAssetRepository.createMedicalRecordAsset(asset)
        Log.d("SaveMedicalRecordFileUseCase", "Asset created with id $assetId")
        return true
    }
}
