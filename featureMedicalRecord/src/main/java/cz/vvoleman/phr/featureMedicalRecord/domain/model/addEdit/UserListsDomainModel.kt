package cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalWorkerDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.ProblemCategoryDomainModel

data class UserListsDomainModel(
    val problemCategories: List<ProblemCategoryDomainModel>,
    val medicalWorkers: List<MedicalWorkerDomainModel>
)
