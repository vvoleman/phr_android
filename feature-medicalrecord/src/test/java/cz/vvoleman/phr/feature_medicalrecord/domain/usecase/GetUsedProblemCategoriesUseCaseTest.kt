package cz.vvoleman.phr.feature_medicalrecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedProblemCategoriesRepository
import cz.vvoleman.phr.feature_medicalrecord.test.coroutine.FakeCoroutineContextProvider
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class GetUsedProblemCategoriesUseCaseTest {

    private lateinit var useCase: GetUsedProblemCategoriesUseCase

    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @Mock
    private lateinit var getUsedProblemCategoriesRepository: GetUsedProblemCategoriesRepository

    @BeforeEach
    fun setUp() {
        coroutineContextProvider = FakeCoroutineContextProvider
        useCase = GetUsedProblemCategoriesUseCase(getUsedProblemCategoriesRepository, coroutineContextProvider)
    }

    @Test
    fun `Get Used Problem Categories, valid`() = runTest {
        val patientId = "1"
        val willReturn = listOf(
            ProblemCategoryDomainModel(
                id = "1",
                name = "Category 1",
                color = "",
                patientId = patientId
            ),
            ProblemCategoryDomainModel(
                id = "2",
                name = "Category 2",
                color = "",
                patientId = patientId
            )
        )

        given(getUsedProblemCategoriesRepository.getUsedProblemCategories(patientId)).willReturn(willReturn)

        val actualValue = useCase.executeInBackground(patientId)

        assertEquals(willReturn, actualValue)
    }
}
