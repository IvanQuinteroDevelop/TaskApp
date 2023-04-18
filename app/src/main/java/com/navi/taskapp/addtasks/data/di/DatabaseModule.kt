package com.navi.taskapp.addtasks.data.di

import android.content.Context
import androidx.room.Room
import com.navi.taskapp.addtasks.data.TaskDao
import com.navi.taskapp.addtasks.data.TaskDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TaskDataBase {
        return Room.databaseBuilder(appContext, TaskDataBase::class.java, "TaskDataBase").build()
    }

    @Provides
    fun provideDao(taskDataBase: TaskDataBase): TaskDao {
        return taskDataBase.taskDao()
    }
}