package com.main.biometric

import androidx.multidex.MultiDexApplication
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.main.biometric.activities.AppLockActivity

class FreshWorksApplication : MultiDexApplication(), LifecycleObserver {

    private lateinit var appComponent: AppComponent

    fun getAppComponent(): AppComponent = appComponent

    companion object {
        operator fun get(context: Context): FreshWorksApplication {
            return context.applicationContext as FreshWorksApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onResume() {
        AppLockActivity.start(this)
    }
}