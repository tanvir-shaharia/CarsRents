package com.example.carsrent.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsrent.R;

public class Viewholder extends RecyclerView.ViewHolder {
   public TextView name,time,car_model;
   public ImageView image;
    public Viewholder(@NonNull View itemView) {
        super(itemView);

        name =itemView.findViewById(R.id.name);
        time =itemView.findViewById(R.id.time);
        car_model =itemView.findViewById(R.id.car_model);
        image =itemView.findViewById(R.id.image);
    }
}
