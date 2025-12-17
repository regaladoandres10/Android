package com.example.tasks.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tasks.data.local.dao.MultimediaDao
import com.example.tasks.data.local.dao.NoteDao
import com.example.tasks.data.local.dao.TaskDao
import com.example.tasks.data.local.entities.Multimedia
import com.example.tasks.data.local.entities.Note
import com.example.tasks.data.local.entities.Task

@Database(
    entities =[Task::class, Note::class, Multimedia::class ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun noteDao(): NoteDao
    abstract fun mediaDao(): MultimediaDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, //Contexto de la aplicacion
                    AppDatabase::class.java, //La clase de la base de datos
                    "task_database" //Nombre de la base de datos
                )
                    .fallbackToDestructiveMigration()
                    .build().also {
                    Instance = it
                }
            }
        }
    }
}