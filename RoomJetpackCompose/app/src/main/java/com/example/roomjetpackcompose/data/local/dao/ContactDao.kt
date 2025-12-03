package com.example.roomjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.roomjetpackcompose.data.local.entities.Contact
import kotlinx.coroutines.flow.Flow

//Data access object
@Dao
interface ContactDao {

    //Inserta si no existe y si existe lo remplaza
    @Upsert
    suspend fun upsertContact(contac: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    //Definir consultas
    @Query("SELECT * FROM Contact ORDER BY firstname ASC")
    fun getContactOrderedByFirstName(): Flow<List<Contact>>

    @Query("SELECT * FROM Contact ORDER BY lastName ASC")
    fun getContactOrderedByLastName(): Flow<List<Contact>>

    @Query("SELECT * FROM Contact ORDER BY phoneNumber ASC")
    fun getContactOrderedByPhoneNumber(): Flow<List<Contact>>

}