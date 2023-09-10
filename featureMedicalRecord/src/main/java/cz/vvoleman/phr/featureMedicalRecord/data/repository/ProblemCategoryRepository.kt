package cz.vvoleman.phr.featureMedicalRecord.data.repository

import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.category.ProblemCategoryDao
import cz.vvoleman.phr.featureMedicalRecord.data.mapper.ProblemCategoryDataSourceToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.domain.model.ProblemCategoryDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetProblemCategoriesForPatientRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetUsedProblemCategoriesRepository
import kotlinx.coroutines.flow.first

class ProblemCategoryRepository(
    private val problemCategoryDao: ProblemCategoryDao,
    private val problemCategoryDataSourceToDomainMapper: ProblemCategoryDataSourceToDomainMapper
) : GetUsedProblemCategoriesRepository, GetProblemCategoriesForPatientRepository {

    override suspend fun getUsedProblemCategories(patientId: String): List<ProblemCategoryDomainModel> {
        return problemCategoryDao.getUsedByPatientId(patientId).first()
            .map { problemCategoryDataSourceToDomainMapper.toDomain(it) }
    }

    override suspend fun getProblemCategoriesForPatient(patientId: String): List<ProblemCategoryDomainModel> {
        return problemCategoryDao.getByPatientId(patientId).first()
            .map { problemCategoryDataSourceToDomainMapper.toDomain(it) }
    }
}
