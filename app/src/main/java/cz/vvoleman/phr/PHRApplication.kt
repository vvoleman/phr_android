package cz.vvoleman.phr

import android.app.Application
import dagger.Component
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus

@HiltAndroidApp
class PHRApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        EventBus.builder().build()
    }

}