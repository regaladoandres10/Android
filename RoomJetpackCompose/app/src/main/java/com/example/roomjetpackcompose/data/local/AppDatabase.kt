package com.example.roomjetpackcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomjetpackcompose.data.local.dao.ContactDao
import com.example.roomjetpackcompose.data.local.entities.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract val dao: ContactDao

}