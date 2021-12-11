package com.example.frndtask.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.frndtask.data.models.TaskDbDetail
import com.example.frndtask.data.models.TaskDetail
import com.example.frndtask.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(val repo:TaskRepository):ViewModel() {

    fun saveData(){
        repo.saveAllData()
    }

    fun getTasks():LiveData<List<TaskDbDetail>>{
        return repo.getAllTasks()
    }

    fun deleteTask(task_id:Int){
        repo.deleteAllTask(task_id)
    }

    fun deleteTask(task:TaskDbDetail){
        repo.deleteTask(task)
    }
    fun clearDb(){
        repo.clearDb()
    }
}