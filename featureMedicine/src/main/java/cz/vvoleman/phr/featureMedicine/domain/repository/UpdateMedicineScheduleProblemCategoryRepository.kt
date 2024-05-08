package cz.vvoleman.phr.featureMedicine.domain.repository

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel

interface UpdateMedicineScheduleProblemCategoryRepository {

    suspend fun updateMedicineScheduleProblemCategory(
        medicineSchedule: MedicineScheduleDomainModel,
        problemCategory: ProblemCategoryDomainModel?
    )

}
