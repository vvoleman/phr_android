package cz.vvoleman.phr.common.data.repository.problemCategory

import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDao
import cz.vvoleman.phr.common.data.mapper.problemCategory.ProblemCategoryDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.data.mapper.problemCategory.ProblemCategoryDataSourceModelToSaveProblemCategoryRequestMapper
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.request.SaveProblemCategoryRequest
import cz.vvoleman.phr.common.domain.repository.problemCategory.DeleteProblemCategoryRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetDefaultProblemCategoryRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoriesRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoryByIdRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.SaveProblemCategoryRepository
import kotlinx.coroutines.flow.firstOrNull

class ProblemCategoryRepository(
    private val problemCategoryDao: ProblemCategoryDao,
    private val problemCategoryMapper: ProblemCategoryDataSourceModelToDomainMapper,
    private val problemCategoryRequestMapper: ProblemCategoryDataSourceModelToSaveProblemCategoryRequestMapper,
) : GetProblemCategoriesRepository,
    GetProblemCategoryByIdRepository,
    DeleteProblemCategoryRepository,
    SaveProblemCategoryRepository,
    GetDefaultProblemCategoryRepository {

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

    override suspend fun saveProblemCategory(request: SaveProblemCategoryRequest): String {
        val model = problemCategoryRequestMapper.toDataSource(request)

        val resultId = problemCategoryDao.insert(model)

        return resultId.toString()
    }

    override suspend fun getDefaultProblemCategory(patientId: String): ProblemCategoryDomainModel? {
        return problemCategoryDao.getDefault(patientId)
            .firstOrNull()
            ?.let { problemCategoryMapper.toDomain(it) }
    }
}
