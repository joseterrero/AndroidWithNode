package com.joseterrero.basicloginapp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joseterrero.basicloginapp.model.TareaResponse;

import java.util.List;

public class MyTareasRecyclerViewAdapter extends RecyclerView.Adapter<MyTareasRecyclerViewAdapter.ViewHolder> {

    private final List<TareaResponse> mValues;
    private Context ctx;

    public MyTareasRecyclerViewAdapter(List<TareaResponse> items, Context ctx) {
        mValues = items;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tareas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitle.setText(holder.mItem.getTitle());
        holder.tvCreatedAt.setText(holder.mItem.getCreatedAt());
        holder.tvBody.setText(holder.mItem.getBody());
        holder.tvCreatedBy.setText(holder.mItem.getCreatedBy().getFullname());
        holder.tvRealizedBy.setText(holder.mItem.getRealizedBy().getFullname());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvCreatedAt;
        public final TextView tvTitle;
        public final TextView tvBody;
        public final TextView tvCreatedBy;
        public final TextView tvRealizedBy;
        public TareaResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvCreatedAt = view.findViewById(R.id.textViewCreatedAt);
            tvTitle = view.findViewById(R.id.textViewTitle);
            tvBody = view.findViewById(R.id.textViewBody);
            tvCreatedBy = view.findViewById(R.id.textViewCreatedBy);
            tvRealizedBy = view.findViewById(R.id.textViewRealizedBy);
        }
    }
}
