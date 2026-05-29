package data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import data.local.database.data.local.database.AppDatabase

fun getDatabaseBuilder(
    context: Context
): RoomDatabase.Builder<AppDatabase> {

    return Room.databaseBuilder<AppDatabase>(
        context =
            context,
        name =
            "appsice.db"
    )
}