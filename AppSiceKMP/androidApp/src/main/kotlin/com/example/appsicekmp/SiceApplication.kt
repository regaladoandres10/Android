package com.example.appsicekmp

import android.app.Application
import di.AppContainer
import di.DefaultAppContainer

class SiceApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}