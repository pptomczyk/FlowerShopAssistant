package com.example.wianki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.wianki.Adapter.OrderAdapter;
import com.example.wianki.Model.OrderModel;
import com.example.wianki.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private DataBaseHelper myDB;
    private CalendarView calendar;
    private TextView dataText;
    private List<OrderModel> orders;
    private String pickedDate;
    private SimpleDateFormat sdf;
    private Date currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        dataText = findViewById(R.id.textView);
        mRecyclerview = findViewById(R.id.recyclerview);
        myDB = new DataBaseHelper(MainActivity.this);
        OrderAdapter adapter = new OrderAdapter(myDB, MainActivity.this);
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        AddNewOrder.class);
                intent.putExtra("date", pickedDate);
                startActivity(intent);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerview);



        LinearLayoutManager linearLayout = new LinearLayoutManager(this);

        mRecyclerview.setLayoutManager(linearLayout);


        //TODO data tekst przy inicjalizacji
        calendar = findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month++;
                pickedDate  = dayOfMonth +"."+month+"."+year;
                dataText.setText(pickedDate);

                orders = myDB.getDataOrders(pickedDate);
                adapter.setOrder(orders);
                mRecyclerview.setAdapter(adapter);
            }
        });



    }
}

