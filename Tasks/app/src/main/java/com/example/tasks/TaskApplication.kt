package com.example.tasks

import android.app.Application
import com.example.tasks.data.local.AppContainer
import com.example.tasks.data.local.AppDataContainer

class TaskApplication: Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}