package cz.vvoleman.phr.data.core

import cz.vvoleman.phr.data.AdapterPair

abstract class BaseData {

    abstract fun getAdapterPair(): AdapterPair
}
