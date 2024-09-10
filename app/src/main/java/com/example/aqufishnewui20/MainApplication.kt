package com.example.aqufishnewui20

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager

class MyApplication : Application(), Configuration.Provider {

    // Implement the workManagerConfiguration property
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG) // Set the logging level as needed
            .build()

    override fun onCreate() {
        super.onCreate()
        // Initialize WorkManager with the custom configuration
        WorkManager.initialize(this, workManagerConfiguration)
    }
}