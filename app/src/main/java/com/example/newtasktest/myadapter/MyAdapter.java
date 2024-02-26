package com.example.newtasktest.myadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newtasktest.R;
import com.example.newtasktest.model.Model;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final ArrayList<Model> model;

    public MyAdapter(ArrayList<Model> model) {
        this.model = model;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_items, parent, false);
        return new ViewHolder(listItem);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
     final Model models= model.get(position);
     holder.name.setText(models.getName());
     holder.designation.setText(models.getDesignation());
     holder.hobby.setText(models.getHobby());
     holder.gender.setText(models.getGender());
     holder.age.setText(models.getAge());
     holder.img.setOnClickListener(v -> {
         model.remove(position);
         notifyItemRemoved(position);
         notifyItemRangeChanged(position, model.size());
     });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,gender,hobby,designation,age;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            gender=itemView.findViewById(R.id.gender);
            hobby= itemView.findViewById(R.id.hobby);
            designation= itemView.findViewById(R.id.designation);
            age=itemView.findViewById(R.id.age);
            img= itemView.findViewById(R.id.trash);
        }
    }
}
