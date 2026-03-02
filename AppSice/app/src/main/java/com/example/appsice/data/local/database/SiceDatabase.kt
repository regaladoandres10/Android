package com.example.appsice.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appsice.data.local.dao.UsuarioDao
import com.example.appsice.data.local.entity.UsuarioEntity

@Database(
    entities = [UsuarioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SiceDatabase : RoomDatabase() {
    //Mandamos llamar los DAOs
    abstract fun usuarioDao(): UsuarioDao

    companion object {
            @Volatile
            private var Instance: SiceDatabase? = null
            fun getDatabase(context: Context): SiceDatabase {
                // if the Instance is not null, return it, otherwise create a new database instance.
                return Instance ?: synchronized(this) {
                    Room.databaseBuilder(context, SiceDatabase::class.java, "sice_database")
                        .build()
                        .also { Instance = it }
                }

        }
    }
}