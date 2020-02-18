package com.joseterrero.basicloginapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.joseterrero.basicloginapp.model.TareaResponse;
import com.joseterrero.basicloginapp.retrofit.generator.AppServiceGenerator;
import com.joseterrero.basicloginapp.retrofit.services.TaskService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareasFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private MyTareasRecyclerViewAdapter adapter;
    private List<TareaResponse> tareasList;
    private RecyclerView recyclerView;
    TaskService taskService;
    AppServiceGenerator appServiceGenerator;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TareasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tareas_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            retrofitInit();
            loadListTareas();
        }
        return view;
    }

    private void retrofitInit() {
        appServiceGenerator = appServiceGenerator.getInstance();
        taskService = appServiceGenerator.getTaskService();
    }

    private void loadListTareas() {
        Call<List<TareaResponse>> call = taskService.getTasks();

        call.enqueue(new Callback<List<TareaResponse>>() {
            @Override
            public void onResponse(Call<List<TareaResponse>> call, Response<List<TareaResponse>> response) {
                if (response.isSuccessful()) {
                    tareasList = response.body();
                    adapter = new MyTareasRecyclerViewAdapter(
                            tareasList, getActivity()
                    );
                    recyclerView.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(getActivity(),"Hubo un error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<TareaResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Hubo un problema de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
