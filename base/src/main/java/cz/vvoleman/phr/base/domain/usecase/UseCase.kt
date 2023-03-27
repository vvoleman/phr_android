package cz.vvoleman.phr.base.domain.usecase

interface UseCase<REQUEST, RESULT> {
    suspend fun execute(input: REQUEST, onResult: (RESULT) -> Unit)
}
