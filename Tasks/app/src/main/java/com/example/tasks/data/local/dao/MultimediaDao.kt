package com.example.tasks.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tasks.data.local.entities.Multimedia
import com.example.tasks.static.OwnerType
import kotlinx.coroutines.flow.Flow

@Dao
interface MultimediaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedia(media: Multimedia)

    @Update
    suspend fun updateMedia(media: Multimedia)

    //Delete one media
    @Delete
    suspend fun deleteMedia(media: Multimedia)

    //Delete all media of task or note
    @Query("DELETE FROM multimedia WHERE ownerId = :ownerId AND ownerType = :ownerType")
    suspend fun deleteByOwner(
        ownerId: Int,
        ownerType: OwnerType
    )

    //Get media de task or note
    @Query("SELECT * FROM multimedia WHERE ownerId = :ownerId AND ownerType = :ownerType ORDER BY createdAt ASC")
    fun getMediaByOwner(
        ownerId: Int,
        ownerType: OwnerType
    ): Flow<List<Multimedia>>

    //Get one media
    @Query("SELECT * FROM multimedia WHERE id = :id")
    fun getMediaById(id: Int): Flow<Multimedia?>
}