package com.joseterrero.basicloginapp.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUI {

    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";

    public SharedPreferencesUI(){}

    private static SharedPreferences getSharedPreferences(){
        return MyApp.getContext()
                .getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static void setStringValue(String dataLabel,String dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel,dataValue);
        editor.commit();
    }

    public static String getStringValue(String dataLabel){
        return getSharedPreferences().getString(dataLabel,null);
    }

    public static void setIntegerValue(String dataLabel,Integer dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(dataLabel,dataValue);
        editor.commit();
    }

    public static Integer getIntegerValue(String dataLabel){
        return getSharedPreferences().getInt(dataLabel,0);
    }
}
