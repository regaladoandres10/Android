package com.example.tasks.data.local

import com.example.tasks.data.local.dao.MultimediaDao
import com.example.tasks.data.local.entities.Multimedia
import com.example.tasks.static.OwnerType
import kotlinx.coroutines.flow.Flow

class OfflineMultimediaRepository(private val mediaDao: MultimediaDao) : MultimediaRepository {

    override suspend fun insertMedia(media: Multimedia) = mediaDao.insertMedia(media)
    override suspend fun updateMedia(media: Multimedia) = mediaDao.updateMedia(media)
    override fun getMediaTask(taskId: Int): Flow<List<Multimedia>> = mediaDao.getMediaByOwner(taskId,
        OwnerType.TASK)
    override fun getMediaNote(noteId: Int): Flow<List<Multimedia>> = mediaDao.getMediaByOwner(noteId,
        OwnerType.NOTE)
    override fun getMediaById(id: Int): Flow<Multimedia?> = mediaDao.getMediaById(id)
    override suspend fun deleteMedia(media: Multimedia) = mediaDao.deleteMedia(media)
    override suspend fun deleteTaskMedia(taskId: Int) = mediaDao.deleteByOwner(taskId, OwnerType.TASK)
    override suspend fun deleteNoteMedia(noteId: Int) = mediaDao.deleteByOwner(noteId, OwnerType.NOTE)
}
