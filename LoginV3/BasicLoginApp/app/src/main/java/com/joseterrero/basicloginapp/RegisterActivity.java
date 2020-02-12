package com.joseterrero.basicloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joseterrero.basicloginapp.model.RegisterResponse;
import com.joseterrero.basicloginapp.model.Registro;
import com.joseterrero.basicloginapp.retrofit.generator.ServiceGenerator;
import com.joseterrero.basicloginapp.retrofit.services.RegisterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etPassword2;
    private Button btnRegister;
    private String username, email, password, password2;
    private TextView tvLogin;
    private RegisterService service;
    private ServiceGenerator serviceGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        retrofitInit();
        findViews();
        events();
    }

    private void retrofitInit() {
        serviceGenerator = ServiceGenerator.getInstance();
        service = serviceGenerator.getRegisterService();
    }

    private void findViews() {
        etUsername = findViewById(R.id.editTextUsernameRegister);
        etEmail = findViewById(R.id.editTextEmailRegister);
        etPassword = findViewById(R.id.editTextPassRegister);
        etPassword2 = findViewById(R.id.editTextPassRegister2);
        btnRegister = findViewById(R.id.buttonRegister);
        tvLogin = findViewById(R.id.textViewLogin);
    }

    private void events() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                password2 = etPassword2.getText().toString();

                if(username.isEmpty())
                    etUsername.setError("El nombre de usuario no puede estar vacío");
                else if(password.isEmpty())
                    etPassword.setError("La contraseña no puede estar vacía");
                else if (email.isEmpty())
                    etEmail.setError("El email no puede quedar vacio");
                else {
                    Registro registro = new Registro(email, username, password, password2);
                    Call<RegisterResponse> call = service.doRegister(registro);

                    call.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            if (response.code() != 200) {
                                Log.e("response", String.valueOf(response.code()));
                                // error
                                Log.e("RequestError", response.message());
                                Toast.makeText(RegisterActivity.this, "Error de petición", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 450) {
                                Log.e("response", String.valueOf(response.code()));
                                // error
                                Log.e("RequestError", response.message());
                                Toast.makeText(RegisterActivity.this, "La contraseña es muy corta", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 451) {
                                Log.e("response", String.valueOf(response.code()));
                                // error
                                Log.e("RequestError", response.message());
                                Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Usuario Correcto", Toast.LENGTH_SHORT).show();
                                // exito
                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                //i.putExtra("username", username);
                                //i.putExtra("password", pass);
                                startActivity(i);
                                Toast.makeText(RegisterActivity.this, "Se ha realizado el registro", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            Log.e("NetworkFailure", t.getMessage());
                            Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
