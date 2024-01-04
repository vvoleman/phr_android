package cz.vvoleman.phr.common.data.repository.problemCategory

import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDao
import cz.vvoleman.phr.common.data.mapper.problemCategory.ProblemCategoryDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.repository.problemCategory.DeleteProblemCategoryRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoriesRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoryByIdRepository
import kotlinx.coroutines.flow.firstOrNull

class ProblemCategoryRepository(
    private val problemCategoryDao: ProblemCategoryDao,
    private val problemCategoryMapper: ProblemCategoryDataSourceModelToDomainMapper
) : GetProblemCategoriesRepository,
    GetProblemCategoryByIdRepository,
    DeleteProblemCategoryRepository {

    override suspend fun deleteProblemCategory(id: String): Boolean {
        problemCategoryDao.delete(id.toInt())

        return true
    }

    override suspend fun getProblemCategories(patientId: String): List<ProblemCategoryDomainModel> {
        return problemCategoryDao.getAll(patientId)
            .firstOrNull()
            ?.map { problemCategoryMapper.toDomain(it) }
            ?: emptyList()
    }

    override suspend fun getProblemCategoryById(id: String): ProblemCategoryDomainModel? {
        return problemCategoryDao.getById(id.toInt())
            .firstOrNull()
            ?.let { problemCategoryMapper.toDomain(it) }
    }
}
