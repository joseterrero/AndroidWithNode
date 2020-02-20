package com.joseterrero.basicloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joseterrero.basicloginapp.model.RegisterResponse;
import com.joseterrero.basicloginapp.retrofit.generator.ServiceGenerator;
import com.joseterrero.basicloginapp.retrofit.services.RegisterService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etPassword2;
    private Button btnRegister;
    private TextView tvLogin;
    private RegisterService service;
    private ServiceGenerator serviceGenerator;
    private static final int READ_REQUEST_CODE = 42;
    ImageView ivAvatar;
    Uri uriSelected;
    Button btnUpload;
    String fileName;
    MultipartBody.Part body;

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
        btnUpload = findViewById(R.id.buttonFichero);
        ivAvatar = findViewById(R.id.imageViewFoto);

        btnRegister.setEnabled(false);
        uriSelected = null;
    }

    private void events() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriSelected != null) {
                    service = serviceGenerator.getRegisterService();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uriSelected);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        int cantBytes;
                        byte[] buffer = new byte[1024*4];

                        while ((cantBytes = bufferedInputStream.read(buffer,0,1024*4)) != -1) {
                            baos.write(buffer,0,cantBytes);
                        }

                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse(getContentResolver().getType(uriSelected)), baos.toByteArray());

                        Cursor cursor = getContentResolver().query(uriSelected, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                            fileName = cursor.getString(nameIndex);
                            cursor.close();
                            body =
                                    MultipartBody.Part.createFormData("avatar", "avatar"+fileName, requestFile);
                        }



                        RequestBody email = RequestBody.create(MultipartBody.FORM, etEmail.getText().toString());
                        RequestBody username = RequestBody.create(MultipartBody.FORM, etUsername.getText().toString());
                        RequestBody password = RequestBody.create(MultipartBody.FORM, etPassword.getText().toString());
                        RequestBody password2 = RequestBody.create(MultipartBody.FORM, etPassword2.getText().toString());

                        if(etUsername.getText().toString().isEmpty())
                            etUsername.setError("El nombre de usuario no puede estar vacío");
                        else if(etPassword.getText().toString().isEmpty())
                            etPassword.setError("La contraseña no puede estar vacía");
                        else if (etEmail.getText().toString().isEmpty())
                            etEmail.setError("El email no puede quedar vacio");
                        else if (uriSelected.toString().isEmpty())
                            btnUpload.setError("No se puede enviar una imagen vacia");
                        else {
                            Call<RegisterResponse> call = service.doRegister(email, username, password, password2, body);

                            call.enqueue(new Callback<RegisterResponse>() {
                                @Override
                                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Usuario Correcto", Toast.LENGTH_SHORT).show();
                                        // exito
                                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(i);
                                        Toast.makeText(RegisterActivity.this, "Se ha realizado el registro", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("Upload error", response.errorBody().toString());
                                        Toast.makeText(RegisterActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                                    Log.e("NetworkFailure", t.getMessage());
                                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
    }

    public void performFileSearch() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + uri.toString());
                //showImage(uri);
                Glide
                        .with(this)
                        .load(uri)
                        .into(ivAvatar);
                uriSelected = uri;
                btnRegister.setEnabled(true);
            }
        }
    }
}
