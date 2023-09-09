package cz.vvoleman.phr.common.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import cz.vvoleman.phr.common.data.datasource.model.PatientDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var patientDao: PatientDao

    override fun onReceive(context: Context?, intent: Intent?) {
        val scope = CoroutineScope(Job() + Dispatchers.Main.immediate)
        Log.d("AlarmReceiver", "onReceive: $intent")
        val content = intent?.getParcelableExtra<Parcelable>(AlarmItem.CONTENT_KEY) ?: return
        val testContent = content as TestContent

        scope.launch {
            patientDao.getById(testContent.id).collect {
                Log.d("AlarmReceiver", "onReceive: $it")
            }
        }
    }
}
