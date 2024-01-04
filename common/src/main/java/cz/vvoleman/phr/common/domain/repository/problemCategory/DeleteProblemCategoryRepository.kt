package cz.vvoleman.phr.common.domain.repository.problemCategory

interface DeleteProblemCategoryRepository {

    suspend fun deleteProblemCategory(id: String): Boolean

}
