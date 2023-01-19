package cz.vvoleman.phr.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cz.vvoleman.phr.util.network.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val connectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    val connectionStatus = connectivityObserver.observer().asLiveData()

}