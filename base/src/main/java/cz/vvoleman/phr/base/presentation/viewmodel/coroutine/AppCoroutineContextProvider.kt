package cz.vvoleman.phr.base.presentation.viewmodel.coroutine

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers

class AppCoroutineContextProvider : CoroutineContextProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
}
