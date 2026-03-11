package com.qbs.laafresh.ui.component

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

class LaaFreshApplications : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)


    }
}