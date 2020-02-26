package com.envilabindonesia.android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by galihadityo on 2019-03-02.
 */
@Entity(tableName = "area")
data class AreaLocal(
    @PrimaryKey(autoGenerate = true)
    val ids: Long = 0,
    @ColumnInfo(name = "name")
    val name: String?
)