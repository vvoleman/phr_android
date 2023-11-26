package cz.vvoleman.phr.featureMedicalRecord.domain.subscriber

import android.content.Context
import android.util.Log
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.common.domain.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.eventBus.CommonEventBus
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerAdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByMedicalWorkerRepository

class MedicalRecordListener(
    private val commonEventBus: CommonEventBus,
    private val getMedicalRecordByMedicalWorkerRepository: GetMedicalRecordByMedicalWorkerRepository,
    private val context: Context,
) : ModuleListener() {
    override val TAG: String = "MedicalRecordListener"

    override suspend fun onInit() {
        super.onInit()
        Log.d(TAG, "onInit")
        commonEventBus.getWorkerAdditionalInfoBus.addListener(TAG) {
            return@addListener onGetMedicalWorkersAdditionalInfoEvent(it)
        }
    }

    private suspend fun onGetMedicalWorkersAdditionalInfoEvent(event: GetMedicalWorkersAdditionalInfoEvent): Map<MedicalWorkerDomainModel, List<MedicalWorkerAdditionalInfoDomainModel>> {
        val map = mutableMapOf<MedicalWorkerDomainModel, List<MedicalWorkerAdditionalInfoDomainModel>>()

        event.medicalWorker.forEach { worker ->
            val records = getMedicalRecordByMedicalWorkerRepository.getMedicalRecordsByMedicalWorker(worker)

            val documentPlural =
                context.resources.getQuantityString(cz.vvoleman.phr.common_datasource.R.plurals.document, records.size)

            map[worker] = listOf(
                MedicalWorkerAdditionalInfoDomainModel(
                    icon = R.drawable.ic_document,
                    text = "${records.size} $documentPlural",
                    onClick = {
                        Log.d(TAG, "Clicked on ${it.name}")
                    }
                )
            )
        }

        return map.toMap()
    }
}
