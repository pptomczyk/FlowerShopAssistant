package com.example.wianki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wianki.Model.OrderModel;
import com.example.wianki.Utils.DataBaseHelper;

public class AddNewOrder extends AppCompatActivity {

    private EditText title;
    private EditText price;
    private EditText color;
    private EditText description;
    private EditText flower;
    private Button cancel;
    private Button save;
    private String date;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        color = findViewById(R.id.color);
        description = findViewById(R.id.description);
        flower = findViewById(R.id.flower);
        cancel = findViewById(R.id.cancelButton);
        save = findViewById(R.id.saveButton);

        date = intent.getStringExtra("date");
        if(intent.hasExtra("bundle")){
            title.setText(bundle.getString("name"));
            price.setText(String.valueOf(bundle.getDouble("price")));
            color.setText(bundle.getString("color"));
            description.setText(bundle.getString("description"));
            flower.setText(bundle.getString("flower"));
            date = bundle.getString("date");
            category = bundle.getString("category");

        }
        DataBaseHelper myDB = new DataBaseHelper(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            //TODO podczas edycji po anulowaniu, przywracań instancję
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderModel order = new OrderModel();
                if (title != null){
                    order.setName(title.getText().toString());
                } else {
                    order.setName("");
                };//TODO porobic zabezpieczenie luk
                order.setFlower(flower.getText().toString());
                order.setColor(color.getText().toString());
                order.setData(date);
                order.setCategory(null); //TODO wybór kategorii przekazać
                order.setStatus(0);
                order.setPrice(Double.parseDouble(price.getText().toString()));
                order.setDescription(description.getText().toString());
                myDB.insertOrder(order);
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });


    }

}
