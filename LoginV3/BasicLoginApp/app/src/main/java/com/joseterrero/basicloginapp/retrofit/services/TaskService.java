package com.joseterrero.basicloginapp.retrofit.services;

import com.joseterrero.basicloginapp.model.AddTarea;
import com.joseterrero.basicloginapp.model.TareaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TaskService {
    @GET("tasks/")
    Call<List<TareaResponse>> getTasks();

    @POST("tasks/")
    Call<TareaResponse> addTask(@Body AddTarea addTarea);
}
