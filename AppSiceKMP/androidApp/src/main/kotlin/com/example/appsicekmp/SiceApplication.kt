package com.example.appsicekmp

import android.app.Application
import data.local.database.di.AppContainer
import data.local.database.di.DefaultAppContainer

class SiceApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}