package com.example.wianki.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wianki.AddNewOrder;
import com.example.wianki.MainActivity;
import com.example.wianki.Model.OrderModel;
import com.example.wianki.R;
import com.example.wianki.Utils.DataBaseHelper;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<OrderModel> mList;
    private MainActivity activity;
    private DataBaseHelper myDB;

    public OrderAdapter(DataBaseHelper myDB, MainActivity activity){
        this.activity = activity;
        this.myDB  = myDB;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final OrderModel item = mList.get(position);
        holder.mCheckBox.setText(item.getName());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.shortText.setText((String) item.getFlower() +" "+ item.getColor());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    myDB.updateStatus(item.getId(),1);
                }else{
                    myDB.updateStatus(item.getId(),0);
                }
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }
    public Context getContext(){
        return activity;
    }
    public void setOrder(List<OrderModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
    public void deleteOrder(int position){
        OrderModel item = mList.get(position);
        myDB.deleteOrder((item.getId()));
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void editOrder(int position){
        OrderModel item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("name", item.getName());
        bundle.putString("data", item.getData());
        bundle.putString("description", item.getDescription());
        bundle.putString("category", item.getCategory());
        bundle.putString("color", item.getColor());
        bundle.putDouble("price", item.getPrice());
        bundle.putString("flower", item.getFlower());
        bundle.putString("date", item.getData());
        bundle.putString("category", item.getCategory());
        deleteOrder(position);
        Intent intent = new Intent(activity.getApplicationContext(),
                AddNewOrder.class);
        intent.putExtra("bundle",bundle);
        activity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(mList==null) return 0;
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        TextView shortText;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            shortText = itemView.findViewById(R.id.shortText);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
        }
    }

}
