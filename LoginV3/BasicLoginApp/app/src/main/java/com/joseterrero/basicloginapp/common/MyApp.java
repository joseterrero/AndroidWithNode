package com.joseterrero.basicloginapp.common;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static MyApp myApp;

    public static MyApp getInstance(){
        return myApp;
    }

    public static Context getContext(){
        return myApp;
    }

    @Override
    public void onCreate(){
        myApp =this;
        super.onCreate();
    }
}
