package cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit

import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

data class UserListsDomainModel(
    val problemCategories: List<ProblemCategoryDomainModel>,
    val medicalWorkers: List<SpecificMedicalWorkerDomainModel>
)
