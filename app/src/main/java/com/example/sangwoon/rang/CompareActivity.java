package com.example.sangwoon.rang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class CompareActivity extends AppCompatActivity {
    int count ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Button left_product = (Button)findViewById(R.id.left_product_barcode);
        left_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=1;

                new IntentIntegrator(CompareActivity.this).initiateScan();


            }
        });

        Button right_product = (Button)findViewById(R.id.right_product_barcode);
        right_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count =2;

                new IntentIntegrator(CompareActivity.this).initiateScan();

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        //result.getContents() 바코드 번호
        //result.getFormatname 바코드 형식

        //정상적으로 출력되었을때

        if(count==1){
            TextView left_Product_name = (TextView) findViewById(R.id.left_productName);               //바코드 넘버 표출
            left_Product_name.setText(result.getContents());
        }
        else {
            TextView right_Product_name = (TextView) findViewById(R.id.right_productName);               //바코드 넘버 표출
            right_Product_name.setText(result.getContents());
        }

    }
}
