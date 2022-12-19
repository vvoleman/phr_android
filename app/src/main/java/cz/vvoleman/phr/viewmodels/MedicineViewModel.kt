package cz.vvoleman.phr.viewmodels

import androidx.lifecycle.ViewModel
import cz.vvoleman.phr.database.MedicineDao

class MedicineViewModel(private val medicineDao: MedicineDao): ViewModel() {

    fun getAll() = medicineDao.getALl()

    fun getByName(name: String) = medicineDao.getByName(name)

    fun getByNameLike(name: String) = medicineDao.getByNameLike(name)

}
