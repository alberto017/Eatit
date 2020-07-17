package com.example.sanchez.eatit.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.sanchez.eatit.Interface.IItemClickListener;
import com.example.sanchez.eatit.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView lblOrderItemName;
    public TextView lblOrderItemStatus;
    public TextView lblOrderItemAddress;
    public TextView lblOrderItemPhone;
    public TextView lblOrderItemHour;
    public TextView lblOrderItemDate;

    private IItemClickListener iItemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        lblOrderItemName = itemView.findViewById(R.id.lblOrderItemName);
        lblOrderItemStatus = itemView.findViewById(R.id.lblOrderItemStatus);
        lblOrderItemAddress = itemView.findViewById(R.id.lblOrderItemAddress);
        lblOrderItemPhone = itemView.findViewById(R.id.lblOrderItemPhone);
        lblOrderItemDate = itemView.findViewById(R.id.lblOrderItemDate);

        itemView.setOnClickListener(this);
    }//OrderViewHolder

    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }//setiItemClickListener

    @Override
    public void onClick(View view) {
        iItemClickListener.onClick(view,getAdapterPosition(),false);
    }//onClick

}//OrderViewHolder
