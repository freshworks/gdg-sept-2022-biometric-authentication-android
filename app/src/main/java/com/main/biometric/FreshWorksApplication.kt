package com.main.biometric

import android.app.Application
import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.*
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.main.biometric.activities.AppLockActivity

class FreshworksApplication : Application(), LifecycleObserver {

    private lateinit var appComponent: AppComponent
    private lateinit var biometricManager: BiometricManager

    fun getAppComponent(): AppComponent = appComponent

    companion object {
        operator fun get(context: Context): FreshworksApplication {
            return context.applicationContext as FreshworksApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        biometricManager = from(this)
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onResume() {
        if (biometricManager.canAuthenticate(BIOMETRIC_WEAK or DEVICE_CREDENTIAL) == BIOMETRIC_SUCCESS){
            AppLockActivity.start(this)
        }
    }
}