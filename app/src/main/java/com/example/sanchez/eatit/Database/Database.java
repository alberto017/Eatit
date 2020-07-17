package com.example.sanchez.eatit.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.sanchez.eatit.Model.CarritoModel;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "EatitDB.db";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }//Database

    public List<CarritoModel> getCart() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductID", "Quantity", "Price", "Discount"};
        String sqliTable = "OrderDetail";

        sqLiteQueryBuilder.setTables(sqliTable);
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, sqlSelect, null, null, null, null, null);

        final List<CarritoModel> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(new CarritoModel(cursor.getString(cursor.getColumnIndex("ProductID")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))
                ));
            } while (cursor.moveToNext());
        }
        return result;
    }//getCart

    public void addToCart(CarritoModel carritoModel){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail (ProductID,ProductName,Quantity,Price,Discount) VALUES ('%s','%s','%s','%s','%s');",
                carritoModel.getProductID(),
                carritoModel.getProductName(),
                carritoModel.getQuantity(),
                carritoModel.getPrice(),
                carritoModel.getDiscount());
        sqLiteDatabase.execSQL(query);
    }//addToCart

    public void clearCart(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        sqLiteDatabase.execSQL(query);
    }//addToCart

}//Database
