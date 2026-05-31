package com.example.appsicekmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.local.database.di.DefaultAppContainer
import data.local.database.getRoomDatabase
import data.local.database.getDatabaseBuilder
import ui.navigation.SicenetApp


fun main() = application {
    val database =
        getRoomDatabase(
            getDatabaseBuilder()
        )

    val container =
        DefaultAppContainer(database)

    Window(
        onCloseRequest = ::exitApplication,
        title = "AppSiceKMP",
    ) {
        SicenetApp(
            repository = container.snRepository
        )
    }
}