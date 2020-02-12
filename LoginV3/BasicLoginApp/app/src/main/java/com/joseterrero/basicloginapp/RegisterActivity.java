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

import com.joseterrero.basicloginapp.model.User;
import com.joseterrero.basicloginapp.retrofit.generator.ServiceGenerator;
import com.joseterrero.basicloginapp.retrofit.services.RegisterService;

import okhttp3.Call;
import okhttp3.Credentials;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etPassword2;
    Button btnRegister;
    String username, email, password, password2;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.editTextUsernameRegister);
        etEmail = findViewById(R.id.editTextEmailRegister);
        etPassword = findViewById(R.id.editTextPassRegister);
        etPassword2 = findViewById(R.id.editTextPassRegister2);
        btnRegister = findViewById(R.id.buttonRegister);
        tvLogin = findViewById(R.id.textViewLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                password2 = etPassword2.getText().toString();

                String credentials = Credentials.basic(username, password);
                RegisterService service = ServiceGenerator.createService(RegisterService.class, username, password);
                Call<User> call = service.doRegister(credentials);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                        if (response.code() != 200) {

                            Log.e("response",String.valueOf(response.code()));
                            // error
                            Log.e("RequestError", response.message());
                            Toast.makeText(RegisterActivity.this, "Error de petición", Toast.LENGTH_SHORT).show();
                        }

                        else if (response.code() == 450) {

                            Log.e("response",String.valueOf(response.code()));
                            // error
                            Log.e("RequestError", response.message());
                            Toast.makeText(RegisterActivity.this, "La contraseña es muy corta", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.code() == 451) {

                            Log.e("response",String.valueOf(response.code()));
                            // error
                            Log.e("RequestError", response.message());
                            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(RegisterActivity.this, "Usuario Correcto", Toast.LENGTH_SHORT).show();
                            // exito
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            //i.putExtra("username", username);
                            //i.putExtra("password", pass);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
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
