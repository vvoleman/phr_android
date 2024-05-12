package cz.vvoleman.phr.base.domain

import androidx.navigation.NavController

@Suppress("VariableNaming")
abstract class ModuleListener {

    protected abstract val TAG: String

    protected var navController: NavController? = null

    fun setController(navController: NavController) {
        this.navController = navController
    }

    open suspend fun onInit() {
    }

    open suspend fun onDestroy() {
        navController = null
    }
}
