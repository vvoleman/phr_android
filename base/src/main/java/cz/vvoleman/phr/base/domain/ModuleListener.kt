package cz.vvoleman.phr.base.domain

abstract class ModuleListener {

    protected abstract val TAG: String

    open suspend fun onInit() {
    }

    open suspend fun onDestroy() {
    }
}
