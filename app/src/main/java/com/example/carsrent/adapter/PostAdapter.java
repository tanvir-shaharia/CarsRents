package com.example.carsrent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carsrent.Post_Model;
import com.example.carsrent.R;
import com.example.carsrent.viewholder.Viewholder;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<Viewholder> {
    Context context;
    List<Post_Model> post_models;

    public PostAdapter(Context context, List<Post_Model> post_models) {
        this.context = context;
        this.post_models = post_models;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.post_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Post_Model post_model=post_models.get(position);
        holder.name.setText(post_model.getName());
        holder.time.setText(post_model.getTime());
        holder.car_model.setText(post_model.getCarmodel());
        Glide.with(context).load(post_model.getPicture()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return post_models.size();
    }
}
