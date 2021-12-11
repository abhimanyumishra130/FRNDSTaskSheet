package com.example.frndtask.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.frndtask.data.models.TaskDbDetail
import com.example.frndtask.data.models.TaskDetail

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToDB(task:TaskDbDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTasksToDB(task:List<TaskDbDetail>)

    @Query("select * from Task_detail")
    fun getAllTask():LiveData<List<TaskDbDetail>>

    @Delete
    fun deleteTask(task:TaskDbDetail)

    @Query("delete from task_detail")
    fun clearDB()

}