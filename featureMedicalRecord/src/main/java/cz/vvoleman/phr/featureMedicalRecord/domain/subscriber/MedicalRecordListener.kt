package cz.vvoleman.phr.featureMedicalRecord.domain.subscriber

import android.content.Context
import android.net.Uri
import android.util.Log
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.common.domain.event.GetMedicalFacilitiesAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.domain.eventBus.CommonEventBus
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByFacilityRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetMedicalRecordByMedicalWorkerRepository

class MedicalRecordListener(
    private val commonEventBus: CommonEventBus,
    private val getMedicalRecordByMedicalWorkerRepository: GetMedicalRecordByMedicalWorkerRepository,
    private val getMedicalRecordByFacilityRepository: GetMedicalRecordByFacilityRepository,
    private val context: Context,
) : ModuleListener() {
    override val TAG: String = "MedicalRecordListener"

    override suspend fun onInit() {
        super.onInit()
        Log.d(TAG, "onInit")
        commonEventBus.getWorkerAdditionalInfoBus.addListener(TAG) {
            return@addListener onGetMedicalWorkersAdditionalInfoEvent(it)
        }
        commonEventBus.getFacilityAdditionalInfoBus.addListener(TAG) {
            return@addListener onGetMedicalFacilityAdditionalInfoEvent(it)
        }
    }

    private suspend fun onGetMedicalWorkersAdditionalInfoEvent(
        event: GetMedicalWorkersAdditionalInfoEvent
    ): Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>> {
        val map = mutableMapOf<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>()

        event.medicalWorker.forEach { worker ->
            val records = getMedicalRecordByMedicalWorkerRepository.getMedicalRecordsByMedicalWorker(worker)

            val documentPlural =
                context.resources.getQuantityString(cz.vvoleman.phr.common_datasource.R.plurals.document, records.size)

            map[worker] = listOf(
                AdditionalInfoDomainModel(
                    icon = R.drawable.ic_document,
                    text = "${records.size} $documentPlural",
                    onClick = {
                        val uri = Uri.parse("phr://listMedicalRecord/?medicalWorkerId=${worker.id}")
                        navController?.navigate(uri)
                    }
                )
            )
        }

        return map.toMap()
    }

    private suspend fun onGetMedicalFacilityAdditionalInfoEvent(
        event: GetMedicalFacilitiesAdditionalInfoEvent
    ): Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>> {
        val map =
            mutableMapOf<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>>()

        val allRecords = getMedicalRecordByFacilityRepository.getMedicalRecordsByFacility(
            event.medicalFacilities.map { it.id }.distinct(),
            event.patientId
        )

        event.medicalFacilities.forEach { facility ->
            val records = allRecords[facility.id] ?: listOf()

            val documentPlural =
                context.resources.getQuantityString(cz.vvoleman.phr.common_datasource.R.plurals.document, records.size)

            map[facility] = listOf(
                AdditionalInfoDomainModel(
                    icon = R.drawable.ic_document,
                    text = "${records.size} $documentPlural",
                    onClick = if (records.isNotEmpty()) {
                        {
                            val uri = Uri.parse("phr://listMedicalRecord/?medicalFacilityId=${facility.id}")
                            navController?.navigate(uri)
                        }
                    } else { null }
                )
            )
        }

        return map.toMap()
    }
}
