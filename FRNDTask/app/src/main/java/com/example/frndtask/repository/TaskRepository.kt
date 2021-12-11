package com.example.frndtask.repository

import androidx.lifecycle.LiveData
import com.example.frndtask.data.database.TaskDao
import com.example.frndtask.data.models.Task
import com.example.frndtask.data.models.TaskDbDetail
import com.example.frndtask.data.models.TaskDetail
import com.example.frndtask.data.models.request.DeleteDataRequest
import com.example.frndtask.data.models.request.GetDataRequestModel
import com.example.frndtask.di.AppModule
import com.example.frndtask.utils.TaskApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskRepository @Inject constructor(val dao: TaskDao) {
    private val autho:String = "d95a5f11-13ef-419a-be7e-5a64cac73624"
    private val api = AppModule.getInstance().create(TaskApi::class.java)


     fun saveAllData(){
        var list = arrayListOf<TaskDbDetail>()
       CoroutineScope(Dispatchers.IO).launch {
           val response = api.getTasks(GetDataRequestModel(1003)).tasks

           response.forEach {
               val taskDetail = TaskDbDetail(it.task_detail.date,it.task_detail.description,it.task_detail.title,it.task_id)
               list.add(taskDetail)
           }
           dao.insertTasksToDB(list)
       }
    }


    fun getAllTasks():LiveData<List<TaskDbDetail>>{
        return dao.getAllTask()
    }

    fun deleteAllTask(task_id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            api.deleteTask(DeleteDataRequest(task_id,1003))
        }
    }

    fun deleteTask(task:TaskDbDetail){
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteTask(task)
        }
    }

    fun clearDb(){
        CoroutineScope(Dispatchers.IO).launch {
            dao.clearDB()
        }
    }
}