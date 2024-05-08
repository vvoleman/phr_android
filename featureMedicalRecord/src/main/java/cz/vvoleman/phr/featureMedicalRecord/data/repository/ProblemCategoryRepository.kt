package cz.vvoleman.phr.featureMedicalRecord.data.repository

import cz.vvoleman.phr.common.data.mapper.problemCategory.ProblemCategoryDataSourceModelToDomainMapper
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.MedicalRecordDao
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetUsedProblemCategoriesRepository
import kotlinx.coroutines.flow.first

class ProblemCategoryRepository(
    private val medicalRecordDao: MedicalRecordDao,
    private val problemCategoryDataSourceToDomainMapper: ProblemCategoryDataSourceModelToDomainMapper
) : GetUsedProblemCategoriesRepository {

    override suspend fun getUsedProblemCategories(patientId: String): List<ProblemCategoryDomainModel> {
        return medicalRecordDao.getUsedProblemCategories(patientId).first()
            .map { problemCategoryDataSourceToDomainMapper.toDomain(it) }
    }
}
