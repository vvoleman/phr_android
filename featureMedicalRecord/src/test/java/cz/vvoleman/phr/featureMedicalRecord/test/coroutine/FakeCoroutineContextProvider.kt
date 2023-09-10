package cz.vvoleman.phr.featureMedicalRecord.test.coroutine

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers

object FakeCoroutineContextProvider : CoroutineContextProvider {
    override val main = Dispatchers.Unconfined
    override val io = Dispatchers.Unconfined
}
