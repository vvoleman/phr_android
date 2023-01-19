package cz.vvoleman.phr.util.network

import kotlinx.coroutines.flow.Flow

interface IConnectivityObserver {

    fun observer(): Flow<ConnectionStatus>

    enum class ConnectionStatus {
        AVAILABLE, UNAVAILABLE, LOSING, LOST
    }

}