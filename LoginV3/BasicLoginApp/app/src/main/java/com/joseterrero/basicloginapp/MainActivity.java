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

import com.joseterrero.basicloginapp.model.Login;
import com.joseterrero.basicloginapp.model.LoginResponse;
import com.joseterrero.basicloginapp.model.User;
import com.joseterrero.basicloginapp.retrofit.generator.ServiceGenerator;
import com.joseterrero.basicloginapp.retrofit.services.LoginService;

import okhttp3.Credentials;
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
        etUsername = findViewById(R.id.textViewPassword);
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
                if(username.isEmpty())
                    etUsername.setError("El nombre de usuario no puede estar vacío");
                else if(pass.isEmpty())
                    etPassword.setError("La contraseña no puede estar vacía");
                else {
                    Login login = new Login(username, pass);

                    Call<LoginResponse> call = service.doLogin(login);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.code() != 200) {
                                Log.e("response", String.valueOf(response.code()));
                                // error
                                Log.e("RequestError", response.message());
                                Toast.makeText(MainActivity.this, "Error de petición", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 401) {
                                Log.e("response", String.valueOf(response.code()));
                                // error
                                Log.e("RequestError", response.message());
                                Toast.makeText(MainActivity.this, "El usuario no existe o la contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 404) {
                                Log.e("response", String.valueOf(response.code()));
                                // error
                                Log.e("RequestError", response.message());
                                Toast.makeText(MainActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Usuario Correcto", Toast.LENGTH_SHORT).show();
                                // exito
/*                            Intent i = new Intent(MainActivity.this, ListaActivity.class);
                            i.putExtra("username", username);
                            i.putExtra("password", pass);
                            startActivity(i);
 */
                                Toast.makeText(MainActivity.this, "Entramos en la página", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e("NetworkFailure", t.getMessage());
                            Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
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
