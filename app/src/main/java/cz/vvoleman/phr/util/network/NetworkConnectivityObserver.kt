package cz.vvoleman.phr.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectivityObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : IConnectivityObserver {

    private val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observer(): Flow<IConnectivityObserver.ConnectionStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(IConnectivityObserver.ConnectionStatus.AVAILABLE) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(IConnectivityObserver.ConnectionStatus.LOSING) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(IConnectivityObserver.ConnectionStatus.LOST) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(IConnectivityObserver.ConnectionStatus.UNAVAILABLE) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

}