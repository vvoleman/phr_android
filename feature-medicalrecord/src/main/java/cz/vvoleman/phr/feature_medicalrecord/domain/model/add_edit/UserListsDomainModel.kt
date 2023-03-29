package cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit

import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalWorkerDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel

data class UserListsDomainModel(
    val problemCategories: List<ProblemCategoryDomainModel>,
    val medicalWorkers: List<MedicalWorkerDomainModel>
)
