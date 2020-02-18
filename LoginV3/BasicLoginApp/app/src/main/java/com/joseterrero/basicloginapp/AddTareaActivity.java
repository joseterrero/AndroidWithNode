package com.joseterrero.basicloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joseterrero.basicloginapp.common.SharedPreferencesUI;
import com.joseterrero.basicloginapp.common.Utilidades;
import com.joseterrero.basicloginapp.model.AddTarea;
import com.joseterrero.basicloginapp.model.TareaResponse;
import com.joseterrero.basicloginapp.retrofit.generator.AppServiceGenerator;
import com.joseterrero.basicloginapp.retrofit.services.TaskService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTareaActivity extends AppCompatActivity {

    private TaskService taskService;
    private AppServiceGenerator appServiceGenerator;
    private EditText etTitulo, etDescripcion;
    private Button btnAddTarea;
    String titulo, descripcion;
    Integer usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarea);

        retrofitInit();
        findViews();
        events();
    }

    private void retrofitInit() {
        appServiceGenerator = AppServiceGenerator.getInstance();
        taskService = appServiceGenerator.getTaskService();
    }

    private void findViews() {
        etTitulo = findViewById(R.id.textViewAddTitle);
        etDescripcion = findViewById(R.id.textViewAddDescripcion);
        btnAddTarea = findViewById(R.id.buttonAddTask);
    }

    private void events() {
        btnAddTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = etTitulo.getText().toString();
                descripcion = etDescripcion.getText().toString();
                usuario = SharedPreferencesUI.getIntegerValue(Utilidades.PREF_ID);

                if(titulo.isEmpty()){
                    etTitulo.setError("Eltitulo no puede estar vacío");
                }else if(descripcion.isEmpty()){
                    etDescripcion.setError("La descripción no puede estar vacia");
                }else{
                    AddTarea addTarea = new AddTarea(titulo, descripcion, usuario, usuario);

                    Call<TareaResponse> call = taskService.addTask(addTarea);
                    call.enqueue(new Callback<TareaResponse>() {
                        @Override
                        public void onResponse(Call<TareaResponse> call, Response<TareaResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(AddTareaActivity.this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();
                                finish();
                            } else
                                Toast.makeText(AddTareaActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TareaResponse> call, Throwable t) {
                            Toast.makeText(AddTareaActivity.this, "Hubo un problema de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
