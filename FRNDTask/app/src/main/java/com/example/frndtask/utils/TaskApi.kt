package com.example.frndtask.utils

import com.example.frndtask.data.models.TaskModel
import com.example.frndtask.data.models.request.DeleteDataRequest
import com.example.frndtask.data.models.request.GetDataRequestModel
import com.example.frndtask.data.models.request.PostDataRequest
import retrofit2.http.*

interface TaskApi {

    @POST("api/getCalendarTaskLists")
    suspend fun getTasks(
        @Body data:GetDataRequestModel
    ): TaskModel

    @POST("api/deleteCalendarTask")
    suspend fun deleteTask(
        @Body userDelete:DeleteDataRequest
    )

    @POST("api/storeCalendarTask")
    suspend fun postTask(
        @Body userStore:PostDataRequest
    )

}