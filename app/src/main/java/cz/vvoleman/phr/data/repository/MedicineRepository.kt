package cz.vvoleman.phr.data.repository

import cz.vvoleman.phr.data.core.Medicine
import cz.vvoleman.phr.data.room.medicine.MedicineDao
import cz.vvoleman.phr.data.room.medicine.MedicineEntity
import cz.vvoleman.phr.data.room.medicine.MedicineSubstanceCrossRef
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceDao
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicineRepository @Inject constructor(
    private val medicineDao: MedicineDao,
    private val substanceDao: SubstanceDao
) {

    suspend fun create(medicine: Medicine) {
        // Insert medicine
        // Create substances
        // Create cross references

        medicineDao.insert(MedicineEntity.from(medicine))

        val crossRefs = mutableListOf<MedicineSubstanceCrossRef>()
        for (substance in medicine.substances) {
            substanceDao.insert(SubstanceEntity.from(substance))
            crossRefs.add(MedicineSubstanceCrossRef(medicine.id, substance.id))
        }

        medicineDao.insert(crossRefs)
    }
}
