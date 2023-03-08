package cz.vvoleman.phr.feature_medicalrecord.test.coroutine

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers

object FakeCoroutineContextProvider : CoroutineContextProvider {
    override val main = Dispatchers.Unconfined
    override val io = Dispatchers.Unconfined
}
