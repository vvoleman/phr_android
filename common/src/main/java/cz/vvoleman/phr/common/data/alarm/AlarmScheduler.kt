package cz.vvoleman.phr.common.data.alarm

interface AlarmScheduler {

    fun schedule(item: AlarmItem): Boolean
    fun cancel(item: AlarmItem): Boolean

}