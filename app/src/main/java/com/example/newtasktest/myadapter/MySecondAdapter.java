package com.example.newtasktest.myadapter;

import android.content.Context;
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

public class MySecondAdapter extends RecyclerView.Adapter<MySecondAdapter.ViewHolder> {

    private final ArrayList<Model> model;

    Context context;

    public MySecondAdapter(ArrayList<Model> model,Context context) {
        this.model = model;
        this.context= context;

    }

    @NonNull
    @Override
    public MySecondAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.user_details,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySecondAdapter.ViewHolder holder, int position) {
        Model model1= model.get(position);
        holder.username.setText(model1.getName());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onclickListener.onClick(model1);

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.remove(model1);
                deleteClickListener.onClick(model1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        ImageView update ,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.dis_username);
            update= itemView.findViewById(R.id.update);
            delete= itemView.findViewById(R.id.delete);
        }
    }

    onclickListener onclickListener;
    deleteClickListener deleteClickListener;
    public interface onclickListener{
        void onClick(Model model);
    }

    public void setOnclickListener(onclickListener onclickListener){
        this.onclickListener= onclickListener;
    }

    public interface deleteClickListener{
        void onClick(Model model);
    }

    public void setDeleteClickListener(deleteClickListener deletelickListener){
        this.deleteClickListener= deletelickListener;
    }


}
