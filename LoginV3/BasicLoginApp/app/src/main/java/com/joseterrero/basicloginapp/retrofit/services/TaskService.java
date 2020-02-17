package com.joseterrero.basicloginapp.retrofit.services;

import com.joseterrero.basicloginapp.model.TareaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TaskService {
    @GET("tasks/")
    Call<List<TareaResponse>> getTasks();
}
