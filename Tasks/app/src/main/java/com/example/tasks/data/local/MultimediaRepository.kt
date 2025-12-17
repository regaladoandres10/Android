package com.example.tasks.data.local

import com.example.tasks.data.local.entities.Multimedia
import com.example.tasks.static.OwnerType
import kotlinx.coroutines.flow.Flow

interface MultimediaRepository {

    fun getMediaByOwner(
        ownerId: Int,
        ownerType: OwnerType
    ): Flow<List<Multimedia>>

    fun getMediaTask(taskId: Int): Flow<List<Multimedia>>

    fun getMediaNote(noteId: Int): Flow<List<Multimedia>>

    fun getMediaById(id: Int): Flow<Multimedia?>

    /**
     * Insert item in the data source
     */
    suspend fun insertMedia(item: Multimedia)

    /**
     * Update item in the data source
     */
    suspend fun updateMedia(item: Multimedia)

    /**
     * Delete item from the data source
     */
    suspend fun deleteMedia(item: Multimedia)

    suspend fun deleteTaskMedia(taskId: Int)

    suspend fun deleteNoteMedia(noteId: Int)
    suspend fun deleteByOwner(ownerId: Int, ownerType: OwnerType)
}
