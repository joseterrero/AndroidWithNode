package com.joseterrero.basicloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.joseterrero.basicloginapp.common.SharedPreferencesUI;
import com.joseterrero.basicloginapp.model.TareaResponse;

public class TareaActivity extends AppCompatActivity implements ITareaListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        new SharedPreferencesUI(this);
    }

    @Override
    public void onClickTarea(TareaResponse tr) {

    }
}
