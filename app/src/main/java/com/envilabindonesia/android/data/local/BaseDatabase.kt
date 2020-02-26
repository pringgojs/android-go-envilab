package com.envilabindonesia.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.envilabindonesia.android.data.local.dao.UserDao
import com.envilabindonesia.android.data.local.entity.AreaLocal
import com.envilabindonesia.android.data.local.entity.UserLocal

/**
 * Created by galihadityo on 2019-03-02.
 */

@Database(entities = [UserLocal::class, AreaLocal::class], version = BaseDatabase.VERSION)
abstract class BaseDatabase: RoomDatabase() {

    companion object {
        const val VERSION = 1
    }

    abstract fun userDao(): UserDao

}