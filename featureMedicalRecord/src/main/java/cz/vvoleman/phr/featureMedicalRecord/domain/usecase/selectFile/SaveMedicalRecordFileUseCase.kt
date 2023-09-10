package cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile

import android.util.Log
import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.AddMedicalRecordAssetDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SaveFileRequestDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.CreateMedicalRecordAssetRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile.SaveFileRepository

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
