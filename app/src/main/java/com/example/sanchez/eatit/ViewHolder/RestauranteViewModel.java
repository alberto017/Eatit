package com.example.sanchez.eatit.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanchez.eatit.Interface.IItemClickListener;
import com.example.sanchez.eatit.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestauranteViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView lblRestaurant_Title;
    public ImageView imgRestaurant_image;
    public TextView lblRestaurant_Address;
    public TextView lblRestaurant_Description;
    public TextView lblRestaurant_City;
    public TextView lblRestaurant_Schedule;

    private IItemClickListener iItemClickListener;

    public RestauranteViewModel(@NonNull View itemView) {
        super(itemView);

        lblRestaurant_Title = itemView.findViewById(R.id.lblRestaurant_Title);
        imgRestaurant_image = itemView.findViewById(R.id.imgRestaurant_image);
        lblRestaurant_Address = itemView.findViewById(R.id.lblRestaurant_Address);
        lblRestaurant_Description = itemView.findViewById(R.id.lblRestaurant_Description);
        lblRestaurant_City = itemView.findViewById(R.id.lblRestaurant_City);
        lblRestaurant_Schedule = itemView.findViewById(R.id.lblRestaurant_Schedule);

        itemView.setOnClickListener(this);
    }//RestauranteViewModel

    public void setItemClickListener(IItemClickListener itemClickListener) {
        this.iItemClickListener = itemClickListener;
    }//setItemClickListener

    @Override
    public void onClick(View view) {
        iItemClickListener.onClick(view, getAdapterPosition(), false);
    }//onClick
}//RestauranteViewModel
