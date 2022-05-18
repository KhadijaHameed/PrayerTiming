package com.sixlogs.pt.storage.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "qazaPrayers", indices = [Index(value = ["id"], unique = true)])
data class QazaPrayerEntities(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: String?,
    val fajar: Boolean? = false,
    val duhar: Boolean? = false,
    val asar: Boolean? = false,
    val maghrib: Boolean? = false,
    val isha: Boolean? = false,
)