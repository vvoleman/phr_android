package cz.vvoleman.phr.common.data.alarm

abstract class ModuleAlarmManager {

    abstract suspend fun initAlarms()
}
