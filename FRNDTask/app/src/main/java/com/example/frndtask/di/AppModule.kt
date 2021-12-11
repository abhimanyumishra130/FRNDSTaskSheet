package com.example.frndtask.di

import android.content.Context
import androidx.room.Room
import com.example.frndtask.data.database.TaskDao
import com.example.frndtask.data.database.TaskRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://13.232.92.136:8084/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext context: Context):TaskRoomDatabase{
        val builder = Room.databaseBuilder(
            context,TaskRoomDatabase::class.java,
            "task_db"
        )
        builder.fallbackToDestructiveMigration()
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(db:TaskRoomDatabase): TaskDao{
        return db.getDao()
    }

}