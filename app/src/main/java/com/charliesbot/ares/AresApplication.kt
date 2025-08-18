package com.charliesbot.ares

import android.app.Application
import com.charliesbot.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AresApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AresApplication)
            modules(appModule)
        }
    }
}