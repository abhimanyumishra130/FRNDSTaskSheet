package com.example.frndtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frndtask.data.models.Task
import com.example.frndtask.data.models.TaskDbDetail
import com.example.frndtask.data.models.TaskDetail
import com.example.frndtask.databinding.ActivityMainBinding
import com.example.frndtask.ui.OnClickListener
import com.example.frndtask.ui.TaskAdapter
import com.example.frndtask.viewModel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var  binding:ActivityMainBinding
    private var list = arrayListOf<TaskDbDetail>()

    private lateinit var adapter:TaskAdapter
    private val viewModel:TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
    }


     fun setRecyclerView() {

         viewModel.clearDb()
         viewModel.saveData()
         viewModel.getTasks().observe(this, Observer {
             list.clear()
             list.addAll(it)
             adapter.notifyDataSetChanged()
         })
         adapter = TaskAdapter(list,this)
         val linearLayoutManager = LinearLayoutManager(this)
         binding.apply {
             rcvTasks.adapter = adapter
             rcvTasks.layoutManager = linearLayoutManager
         }
    }


    override fun deleteItem(task: TaskDbDetail) {
        viewModel.deleteTask(task.task_id)
//        viewModel.deleteTask(task)
        setRecyclerView()
    }
}