package com.ghamdandev.demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class HiltApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (com.google.firebase.BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}