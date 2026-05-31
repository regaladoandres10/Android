package data.local.database

import androidx.room.Room
import androidx.room.RoomDatabase
import data.local.database.data.local.database.AppDatabase
import java.io.File

fun getDatabaseBuilder() : RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("user.home"), "appsice.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath
    )
}

fun pruebaDesktop() {

}
