package cz.vvoleman.phr.base.presentation.viewmodel.usecase

import cz.vvoleman.phr.base.domain.usecase.UseCaseExecutor
import kotlinx.coroutines.CoroutineScope

typealias UseCaseExecutorProvider =
        @JvmSuppressWildcards (coroutineScope: CoroutineScope) -> UseCaseExecutor
