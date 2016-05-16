package com.example.sangwoon.rang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Button barcode_button = (Button) findViewById(R.id.barcode_button);
        barcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent barcode_activity = new Intent(MainActivity.this, BarcodeActivity.class);
                startActivity(barcode_activity);


            }
        });

        Button compare_button = (Button)findViewById(R.id.compare);
        compare_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compare_activity = new Intent(MainActivity.this, CompareActivity.class);
                startActivity(compare_activity);
            }
        });
    }


    }



