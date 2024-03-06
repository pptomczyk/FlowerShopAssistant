package com.example.wianki.Utils;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.wianki.Model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "PRICE";
    private static final String COL_4 = "COLOR";
    private static final String COL_5 = "FLOWER";
    private static final String COL_6 = "DESCRIPTION";
    private static final String COL_7 = "CATEGORY";
    private static final String COL_8 = "STATUS";
    private static final String COL_9= "DATE";
    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PRICE DECIMAL, COLOR TEXT, FLOWER TEXT, DESCRIPTION TEXT,CATEGORY TEXT,STATUS INT,DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertOrder(OrderModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(COL_2, model.getName());
        values.put(COL_3, model.getPrice());
        values.put(COL_4, model.getColor());
        values.put(COL_5, model.getFlower());
        values.put(COL_6, model.getDescription());
        values.put(COL_7, model.getCategory());
        values.put(COL_8, model.getStatus());
        values.put(COL_9, model.getData());
        db.insert(TABLE_NAME, null, values);
    }

    public void updateOrder(int id, OrderModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(COL_2, model.getName());
        values.put(COL_3, model.getPrice());
        values.put(COL_4, model.getColor());
        values.put(COL_5, model.getFlower());
        values.put(COL_6, model.getDescription());
        values.put(COL_7, model.getCategory());
        values.put(COL_8, model.getStatus());
        values.put(COL_9, model.getData());
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(COL_8, status);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteOrder(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?", new String[]{String.valueOf(id)});

    }
    @SuppressLint("Range")
    public List<OrderModel> getDataOrders(String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        List<OrderModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            String selection = COL_9 + " = ?";
            String[] selectionArgs = {data};
            cursor = db.query(TABLE_NAME, null,selection,selectionArgs, null,null,null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        OrderModel order = new OrderModel();
                        order.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        order.setName(cursor.getString(cursor.getColumnIndex(COL_2)));
                        order.setPrice(cursor.getDouble(cursor.getColumnIndex(COL_3)));
                        order.setColor(cursor.getString(cursor.getColumnIndex(COL_4)));
                        order.setFlower(cursor.getString(cursor.getColumnIndex(COL_5)));
                        order.setDescription(cursor.getString(cursor.getColumnIndex(COL_6)));
                        order.setCategory(cursor.getString(cursor.getColumnIndex(COL_7)));
                        order.setStatus(cursor.getInt(cursor.getColumnIndex(COL_8)));
                        order.setData(cursor.getString(cursor.getColumnIndex(COL_9)));
                        modelList.add(order);
                    }while(cursor.moveToNext());
                }
            }

        }finally{
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
    @SuppressLint("Range")
    public List<OrderModel> getAllOrders(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        List<OrderModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME, null,null,null, null,null,null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        OrderModel order = new OrderModel();
                        order.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        order.setName(cursor.getString(cursor.getColumnIndex(COL_2)));
                        order.setPrice(cursor.getDouble(cursor.getColumnIndex(COL_3)));
                        order.setColor(cursor.getString(cursor.getColumnIndex(COL_4)));
                        order.setFlower(cursor.getString(cursor.getColumnIndex(COL_5)));
                        order.setDescription(cursor.getString(cursor.getColumnIndex(COL_6)));
                        order.setCategory(cursor.getString(cursor.getColumnIndex(COL_7)));
                        order.setStatus(cursor.getInt(cursor.getColumnIndex(COL_8)));
                        order.setData(cursor.getString(cursor.getColumnIndex(COL_9)));
                        modelList.add(order);
                    }while(cursor.moveToNext());
                }
            }

        }finally{
            db.endTransaction();
            cursor.close();
        }
        return modelList;

    }
}
