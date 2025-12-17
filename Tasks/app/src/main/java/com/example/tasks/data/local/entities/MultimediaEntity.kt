package com.example.tasks.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.tasks.static.FileType
import com.example.tasks.static.OwnerType

//Foreign key
@Entity(
    tableName = "multimedia",
    indices = [Index("ownerId"), Index("ownerType")]
)
data class Multimedia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ownerId: Int,
    val ownerType: OwnerType, //TASK or NOTE
    val uri: String,
    val type: FileType,
    val createdAt: Long = System.currentTimeMillis()
)