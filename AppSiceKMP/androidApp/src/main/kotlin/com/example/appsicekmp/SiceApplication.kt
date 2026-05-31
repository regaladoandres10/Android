package com.example.appsicekmp

import android.app.Application
import data.local.database.di.AppContainer
import data.local.database.di.DefaultAppContainer
import data.local.database.getDatabaseBuilder
import data.local.database.getRoomDatabase


class SiceApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        val database =
            getRoomDatabase(
                getDatabaseBuilder(this)
            )

        container =
            DefaultAppContainer(database)
    }

}