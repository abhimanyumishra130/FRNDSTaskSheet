package com.example.frndtask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.frndtask.R
import com.example.frndtask.data.models.Task
import com.example.frndtask.data.models.TaskDbDetail
import com.example.frndtask.data.models.TaskDetail
import com.example.frndtask.databinding.ItemLayoutBinding

class TaskAdapter(val list: List<TaskDbDetail>, val onClickListener: OnClickListener):RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_layout,parent,false),onClickListener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskDetail = list[position]
        holder.setData(taskDetail)
    }

    override fun getItemCount(): Int {
       return list.size
    }
}

class TaskViewHolder(val itemLayoutBinding: ItemLayoutBinding,val onClickListener: OnClickListener):RecyclerView.ViewHolder(itemLayoutBinding.root){

    fun setData(task: TaskDbDetail){
        itemLayoutBinding.task = task
        itemLayoutBinding.tvDelete.setOnClickListener {
            onClickListener.deleteItem(task)
        }
    }
}

interface OnClickListener{
    fun deleteItem(task: TaskDbDetail)
}