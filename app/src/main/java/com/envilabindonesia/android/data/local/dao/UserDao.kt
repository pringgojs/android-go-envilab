package com.envilabindonesia.android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.envilabindonesia.android.data.local.entity.UserLocal

/**
 * Created by galihadityo on 2019-03-02.
 */

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): UserLocal

    @Insert
    fun insert(userLocal: UserLocal)

}