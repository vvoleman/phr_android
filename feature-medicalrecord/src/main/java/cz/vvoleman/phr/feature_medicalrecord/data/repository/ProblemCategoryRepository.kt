package cz.vvoleman.phr.feature_medicalrecord.data.repository

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDao
import cz.vvoleman.phr.feature_medicalrecord.data.mapper.ProblemCategoryDataSourceToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedProblemCategoriesRepository
import kotlinx.coroutines.flow.first

class ProblemCategoryRepository(
    private val problemCategoryDao: ProblemCategoryDao,
    private val problemCategoryDataSourceToDomainMapper: ProblemCategoryDataSourceToDomainMapper
) : GetUsedProblemCategoriesRepository {

    override suspend fun getUsedProblemCategories(patientId: String): List<ProblemCategoryDomainModel> {
        return problemCategoryDao.getUsedByPatientId(patientId).first()
            .map { problemCategoryDataSourceToDomainMapper.toDomain(it) }
    }
}