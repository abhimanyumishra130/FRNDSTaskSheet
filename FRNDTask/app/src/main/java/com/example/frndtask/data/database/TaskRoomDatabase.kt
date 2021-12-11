package com.example.frndtask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.frndtask.data.models.TaskDbDetail
import com.example.frndtask.data.models.TaskDetail

@Database(entities = [TaskDbDetail::class],version = 2)
abstract class TaskRoomDatabase():RoomDatabase() {
abstract fun getDao():TaskDao
}