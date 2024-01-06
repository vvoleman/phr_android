package cz.vvoleman.phr.common.domain.model.problemCategory.request

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

sealed class DataDeleteType {
    data class MoveToAnother(val backupProblemCategory: ProblemCategoryDomainModel? = null) : DataDeleteType()
    object DeleteData : DataDeleteType()
}
