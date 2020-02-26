package com.envilabindonesia.android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by galihadityo on 2019-03-02.
 */
@Entity(tableName = "user")
data class UserLocal(
    @PrimaryKey(autoGenerate = true)
    val ids: Long = 0,
    @ColumnInfo(name = "body")
    val body: String?,
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "userId")
    val userId: Int?
)