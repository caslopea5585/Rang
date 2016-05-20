package com.example.sangwoon.rang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



public class BarcodeActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        new IntentIntegrator(BarcodeActivity.this).initiateScan();


        //재촬영
        Button re_barcode_search = (Button)findViewById(R.id.re_barcode_search);
        re_barcode_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            TextView Product_name = (TextView) findViewById(R.id.Product_name);               //바코드 넘버 표출
            Product_name.setText(result.getContents());
           //Search_History.ListViewAdapter.class.getClasses();
            //write_history();


        }

    }


}





