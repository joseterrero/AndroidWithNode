package com.joseterrero.basicloginapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joseterrero.basicloginapp.common.SharedPreferencesUI;
import com.joseterrero.basicloginapp.model.TareaResponse;
import com.joseterrero.basicloginapp.retrofit.generator.ServiceGenerator;
import com.joseterrero.basicloginapp.retrofit.services.TaskService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class TareasFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ITareaListener mListener;
    private MyTareasRecyclerViewAdapter adapter;
    private List<TareaResponse> tareasList;
    private Context ctx;
    private RecyclerView recyclerView;
    private TaskService service;
    private SharedPreferencesUI sharedPreferencesUI;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TareasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            tareasList = new ArrayList<>();
            service = ServiceGenerator.createService(TaskService.class);
            new getTareas().execute();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ITareaListener) {
            mListener = (ITareaListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ITareaListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class getTareas extends AsyncTask<Void, Void, List<TareaResponse>> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<TareaResponse> doInBackground(Void... voids) {
            Call<List<TareaResponse>> call = service.getTasks();

            Response<List<TareaResponse>> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response.isSuccessful())
                tareasList = response.body();

            return tareasList;
        }

        @Override
        protected void onPostExecute(List<TareaResponse> tareaResponses) {
            super.onPostExecute(tareaResponses);

            adapter = new MyTareasRecyclerViewAdapter(
                    tareaResponses, ctx, R.layout.fragment_tareas, mListener
            );
            recyclerView.setAdapter(adapter);
        }
    }
}
