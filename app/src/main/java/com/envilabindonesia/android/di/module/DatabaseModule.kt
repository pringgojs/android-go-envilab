package com.envilabindonesia.android.di.module

import android.content.Context
import androidx.room.Room
import com.envilabindonesia.android.data.local.BaseDatabase
import com.envilabindonesia.android.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by galihadityo on 2019-03-02.
 */

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): BaseDatabase {
        return Room.databaseBuilder(context, BaseDatabase::class.java, "basedatabase")
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: BaseDatabase): UserDao {
        return database.userDao()
    }

}