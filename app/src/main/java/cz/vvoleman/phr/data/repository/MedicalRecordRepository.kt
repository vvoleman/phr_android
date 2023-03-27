package cz.vvoleman.phr.data.repository

import cz.vvoleman.phr.data.room.medical_record.MedicalRecordDao
import cz.vvoleman.phr.data.room.medical_record.MedicalRecordWithDetails
import cz.vvoleman.phr.data.room.medical_record.asset.MedicalRecordAssetDao
import javax.inject.Inject

class MedicalRecordRepository @Inject constructor(
    private val medicalRecordDao: MedicalRecordDao,
    private val assetDao: MedicalRecordAssetDao
) {

    suspend fun delete(medicalRecord: MedicalRecordWithDetails) {
        // Delete assets
        medicalRecord.toMedicalRecord().id?.let {
            assetDao.deleteAllForRecord(it)
        }

        medicalRecordDao.delete(medicalRecord.medicalRecord)
    }

}