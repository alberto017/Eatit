package com.example.sanchez.eatit.ViewHolder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanchez.eatit.Interface.IItemClickListener;
import com.example.sanchez.eatit.R;

import androidx.recyclerview.widget.RecyclerView;

public class CategoriaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView lblCategory_title;
    public ImageView lblCategory_image;

    private IItemClickListener iItemClickListener;

    public CategoriaViewHolder(View itemView) {
        super(itemView);

        lblCategory_title = itemView.findViewById(R.id.category_title);
        lblCategory_image = itemView.findViewById(R.id.category_image);

        itemView.setOnClickListener(this);
    }//CategoriaViewHolder

    public void setItemClickListener(IItemClickListener itemClickListener) {
        this.iItemClickListener = itemClickListener;
    }//setItemClickListener

    @Override
    public void onClick(View view) {
        iItemClickListener.onClick(view, getAdapterPosition(), false);
    }//onClick
}//CategoriaViewHolder
