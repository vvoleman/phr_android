package cz.vvoleman.phr.base.domain.usecase

import cz.vvoleman.phr.base.domain.exception.DomainException
import cz.vvoleman.phr.base.domain.exception.UnknownDomainException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UseCaseExecutor(
    private val coroutineScope: CoroutineScope
) {

    fun <OUTPUT> execute(
        useCase: UseCase<Unit, OUTPUT>,
        onSuccess: (OUTPUT) -> Unit,
        onException: (DomainException) -> Unit = {}
    ) {
        coroutineScope.launch {
            execute(useCase, Unit, onSuccess, onException)
        }
    }

    fun <INPUT, OUTPUT> execute(
        useCase: UseCase<INPUT, OUTPUT>,
        value: INPUT,
        onSuccess: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) {
        coroutineScope.launch {
            try {
                useCase.execute(value, onSuccess)
            } catch (ignore: CancellationException) {
            } catch (throwable: Throwable) {
                onException(
                    (throwable as? DomainException)
                        ?: UnknownDomainException(throwable)
                )
            }
        }
    }
}
