package com.example.sangwoon.rang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



public class BarcodeActivity extends AppCompatActivity {


    String food_name;
    int food_calbo,food_fat,food_protein,food_na,food_col,food_energy,test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        new IntentIntegrator(BarcodeActivity.this).initiateScan();





        ImageView[] food_image = new ImageView[1] ;
        food_image[0] = (ImageView)findViewById(R.id.value01_arrow);




        TextView food_name_getvalue = (TextView)findViewById(R.id.food_name);
        food_name_getvalue.setText("0");
        food_name = food_name_getvalue.getText().toString();

        TextView food_calbo_getvalue = (TextView)findViewById(R.id.food_calbo);
        food_calbo_getvalue.setText("0");
        food_calbo = Integer.parseInt(food_calbo_getvalue.getText().toString());

        TextView food_fat_getvalue = (TextView)findViewById(R.id.food_fat);
        food_fat_getvalue.setText("0");
        food_fat = Integer.parseInt(food_fat_getvalue.getText().toString());

        TextView food_protein_getvalue = (TextView)findViewById(R.id.food_protein);
        food_protein_getvalue.setText("0");
        food_protein = Integer.parseInt(food_protein_getvalue.getText().toString());

        TextView food_na_getvalue = (TextView)findViewById(R.id.food_na);
        food_na_getvalue.setText("0");
        food_na = Integer.parseInt(food_na_getvalue.getText().toString());

        TextView food_col_getvalue = (TextView)findViewById(R.id.food_col);
        food_col_getvalue.setText("0");
        food_col = Integer.parseInt(food_col_getvalue.getText().toString());

        TextView food_energy_getvalue = (TextView)findViewById(R.id.food_energy);
        food_energy_getvalue.setText("0");
        food_energy = Integer.parseInt(food_energy_getvalue.getText().toString());

        food_name="0";

        //재촬영
        Button re_barcode_search = (Button)findViewById(R.id.re_barcode_search);
        re_barcode_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //test = ((Search_History)Search_History.mBarcode_Activity_Context).Insert_history();
                new IntentIntegrator(BarcodeActivity.this).initiateScan();

            }
        });


        //메인으로 이동
        Button Go_to_main = (Button)findViewById(R.id.Barcode_to_Main);
        Go_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarcodeActivity.this.finish();


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Intent history= new Intent(this,Search_History.class);


        //result.getContents() 바코드 번호
        //result.getFormatname 바코드 형식

        //정상적으로 출력되었을때

        if (result.getContents() == null ) {
            BarcodeActivity.this.finish();

        } else {
            TextView Product_name = (TextView) findViewById(R.id.food_name);               //바코드 넘버 표출



            Product_name.setText(result.getContents());

        }

       // ((MainActivity)MainActivity.mBarcode_Activity_Context).Insert_history();
    }




}





