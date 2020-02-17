package com.joseterrero.basicloginapp.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUI {

    public static Context context;
    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";


    public SharedPreferencesUI(Context context) {
        this.context = context;
    }

    public SharedPreferencesUI() {
    }

    private static SharedPreferences getSharedPreferences(){
        return context.getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static void setSomeStringValue(String dataLabel, String dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel, dataValue);
        editor.commit();
    }

    public static String getSomeStringValue(String dataLabel) {

        return getSharedPreferences().getString(dataLabel, null);
    }
}
