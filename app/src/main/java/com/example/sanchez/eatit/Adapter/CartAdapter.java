package com.example.sanchez.eatit.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.sanchez.eatit.Interface.IItemClickListener;
import com.example.sanchez.eatit.Model.CarritoModel;
import com.example.sanchez.eatit.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView lblCardItemName;
    public TextView lblCardItemPrice;
    public ImageView imgCardItemCount;

    private IItemClickListener iItemClickListener;

    public void setCartName(TextView lblCartName) {
        this.lblCardItemName = lblCartName;
    }//setCartName

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        lblCardItemName = itemView.findViewById(R.id.lblCartItemName);
        lblCardItemPrice = itemView.findViewById(R.id.lblCartItemPrice);
        imgCardItemCount = itemView.findViewById(R.id.imgCartItemCount);
    }//CartViewHolder

    @Override
    public void onClick(View view) {

    }//onClick

}//CartViewHolder

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<CarritoModel> orderModelList = new ArrayList<>();
    private Context context;

    public CartAdapter(List<CarritoModel> orderModelList, Context context) {
        this.orderModelList = orderModelList;
        this.context = context;
    }//CartAdapter

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }//onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound("" + orderModelList.get(position).getQuantity(), Color.RED);
        holder.imgCardItemCount.setImageDrawable(drawable);

        Locale locale = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt((orderModelList.get(position).getPrice())) * (Integer.parseInt(orderModelList.get(position).getQuantity())));
        holder.lblCardItemPrice.setText(numberFormat.format(price));
        holder.lblCardItemName.setText(orderModelList.get(position).getProductName());
    }//onBindViewHolder

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }//getItemCount

}//CartAdapter

