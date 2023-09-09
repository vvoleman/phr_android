package cz.vvoleman.phr.base.domain.usecase

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<REQUEST, RESULT> {

    abstract fun execute(request: REQUEST): Flow<RESULT>
}
