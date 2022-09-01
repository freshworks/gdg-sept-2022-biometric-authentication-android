package com.main.biometric

import android.app.Application
import com.main.biometric.activities.AppLockActivity
import com.main.biometric.activities.WebViewActivity
import com.main.biometric.module.AppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton
import dagger.BindsInstance


@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: FreshworksApplication)

    fun inject(activity: AppLockActivity)

    fun inject(activity: WebViewActivity)
}
