package com.joseterrero.basicloginapp.retrofit.generator;

import com.joseterrero.basicloginapp.common.Utilidades;
import com.joseterrero.basicloginapp.retrofit.services.TaskService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppServiceGenerator {

    private static AppServiceGenerator appServiceGenerator = null;
    private TaskService taskService;
    private Retrofit retrofit;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(logging);

    public AppServiceGenerator(){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new AuthenticationInterceptor());
        OkHttpClient okHttpClient = okHttpClientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Utilidades.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        taskService = retrofit.create(TaskService.class);
    }

    public static AppServiceGenerator getInstance(){
        if(appServiceGenerator==null){
            appServiceGenerator = new AppServiceGenerator();
        }
        return appServiceGenerator;
    }

    public TaskService getTaskService(){
        return taskService;
    }

}
