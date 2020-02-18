package com.joseterrero.basicloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joseterrero.basicloginapp.common.SharedPreferencesUI;
import com.joseterrero.basicloginapp.common.Utilidades;
import com.joseterrero.basicloginapp.model.Login;
import com.joseterrero.basicloginapp.model.LoginResponse;
import com.joseterrero.basicloginapp.retrofit.generator.ServiceGenerator;
import com.joseterrero.basicloginapp.retrofit.services.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private String username, pass;
    private TextView tvRegister;
    private LoginService service;
    private ServiceGenerator serviceGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofitInit();
        findViews();
        events();
    }

    private void retrofitInit() {
        serviceGenerator = ServiceGenerator.getInstance();
        service = serviceGenerator.getLoginService();
    }

    private void findViews() {
        etUsername = findViewById(R.id.textViewUsername);
        etPassword = findViewById(R.id.textViewPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        tvRegister = findViewById(R.id.textViewRegister);
    }

    private void events() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString();
                pass = etPassword.getText().toString();

                if(username.isEmpty()){
                    etUsername.setError("El usuario esta vacío");
                }else if(pass.isEmpty()){
                    etPassword.setError("La contraseña esta vacía");
                }else{
                    Login login = new Login(username, pass);

                    Call<LoginResponse> call = service.doLogin(login);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login realizado correctamente", Toast.LENGTH_SHORT).show();
                                SharedPreferencesUI.setStringValue(Utilidades.PREF_TOKEN, response.body().getToken());
                                SharedPreferencesUI.setStringValue(Utilidades.PREF_USERNAME, response.body().getUsername());
                                SharedPreferencesUI.setIntegerValue(Utilidades.PREF_ID,Integer.parseInt(response.body().getId()));
                                finish();
                                Intent i = new Intent(MainActivity.this, TareaActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(MainActivity.this, "Hay un error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Hubo un problema de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

}
