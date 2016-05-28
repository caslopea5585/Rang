package com.example.sangwoon.rang;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class CompareActivity extends AppCompatActivity {
    String count = "0";
    String barcode_num="";
    //value1 -  애너지, 탄수화물 , 지방, 단백질, 나트륨, 콜레스트롤
   int left_value_1,left_value_2,left_value_3,left_value_4,left_value_5,left_value_6;
   int right_value_1,right_value_2,right_value_3,right_value_4,right_value_5,right_value_6;
    String[][] parsedData={};
    TextView Left_value1,Left_value2,Left_value3,Left_value4,Left_value5,Left_value6;
    TextView Right_value1,Right_value2,Right_value3,Right_value4,Right_value5,Right_value6;
    TextView left_Product_name,right_Product_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);



        Left_value1 = (TextView)findViewById(R.id.Left_value1);
        Left_value2 = (TextView)findViewById(R.id.Left_value2);
        Left_value3 = (TextView)findViewById(R.id.Left_value3);
        Left_value4 = (TextView)findViewById(R.id.Left_value4);
        Left_value5 = (TextView)findViewById(R.id.Left_value5);
        Left_value6 = (TextView)findViewById(R.id.Left_value6);


        Right_value1 = (TextView)findViewById(R.id.Right_value1);
        Right_value2 = (TextView)findViewById(R.id.Right_value2);
        Right_value3 = (TextView)findViewById(R.id.Right_value3);
        Right_value4 = (TextView)findViewById(R.id.Right_value4);
        Right_value5 = (TextView)findViewById(R.id.Right_value5);
        Right_value6 = (TextView)findViewById(R.id.Right_value6);



        Left_value1.setText("2");
        Left_value2.setText("2");
        Left_value3.setText("2");
        Left_value4.setText("2");
        Left_value5.setText("2");
        Left_value6.setText("2");

        Right_value1.setText("1");
        Right_value2.setText("2");
        Right_value3.setText("3");
        Right_value4.setText("2");
        Right_value5.setText("3");
        Right_value6.setText("3");


        left_value_1 = Integer.parseInt(Left_value1.getText().toString());
        left_value_2 = Integer.parseInt(Left_value2.getText().toString());
        left_value_3 = Integer.parseInt(Left_value3.getText().toString());
        left_value_4 = Integer.parseInt(Left_value4.getText().toString());
        left_value_5 = Integer.parseInt(Left_value5.getText().toString());
        left_value_6 = Integer.parseInt(Left_value6.getText().toString());



        right_value_1 = Integer.parseInt(Right_value1.getText().toString());
        right_value_2 = Integer.parseInt(Right_value2.getText().toString());
        right_value_3 = Integer.parseInt(Right_value3.getText().toString());
        right_value_4 = Integer.parseInt(Right_value4.getText().toString());
        right_value_5 = Integer.parseInt(Right_value5.getText().toString());
        right_value_6 = Integer.parseInt(Right_value6.getText().toString());


        ImageView[] arrow = new ImageView[6] ;
        arrow[0] = (ImageView)findViewById(R.id.value01_arrow);
        arrow[1] = (ImageView)findViewById(R.id.value02_arrow);
        arrow[2] = (ImageView)findViewById(R.id.value03_arrow);
        arrow[3] = (ImageView)findViewById(R.id.value04_arrow);
        arrow[4] = (ImageView)findViewById(R.id.value05_arrow);
        arrow[5] = (ImageView)findViewById(R.id.value06_arrow);


        if(left_value_1 !=0 && right_value_1 !=0){
            if(left_value_1==right_value_1){
                arrow[0].setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_1 > right_value_1){
                arrow[0].setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow[0].setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }

        if(left_value_2 !=0 && right_value_2 !=0){
            if(left_value_2==right_value_2){
                arrow[1].setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_2 > right_value_2){
                arrow[1].setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow[1].setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }

        if(left_value_3 !=0 && right_value_3 !=0){
            if(left_value_3==right_value_3){
                arrow[2].setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_3 > right_value_3){
                arrow[2].setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow[2].setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }

        if(left_value_4 !=0 && right_value_4 !=0){
            if(left_value_4==right_value_4){
                 arrow[3].setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_4 > right_value_4){
                arrow[3].setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow[3].setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }

        if(left_value_5 !=0 && right_value_5 !=0){
            if(left_value_5==right_value_5){
                arrow[4].setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_5 > right_value_5){
                arrow[4].setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow[4].setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }

        if(left_value_6 !=0 && right_value_6 !=0){
            if(left_value_6==right_value_6){
                arrow[5].setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_6 > right_value_6){
                arrow[5].setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow[5].setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }



        Button left_product = (Button)findViewById(R.id.left_product_barcode);
        left_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count="1";
                new IntentIntegrator(CompareActivity.this).initiateScan();


            }
        });

        Button right_product = (Button)findViewById(R.id.right_product_barcode);
        right_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count ="2";

                new IntentIntegrator(CompareActivity.this).initiateScan();

            }
        });
        value_compare();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        //result.getContents() 바코드 번호
        //result.getFormatname 바코드 형식

        //정상적으로 출력되었을때
        Log.d("qwe333f",count);
        if(count=="1"){
            left_Product_name = (TextView) findViewById(R.id.left_productName);               //바코드 넘버 표출
            barcode_num = result.getContents();
            insertToDatabase(result.getContents(),count);


            //left_Product_name.setText(result.getContents());
        }
        else if(count=="2") {
            right_Product_name = (TextView) findViewById(R.id.right_productName);               //바코드 넘버 표출
            barcode_num = result.getContents();
            insertToDatabase(result.getContents(),count);

            //right_Product_name.setText(result.getContents());
        }

    }
    public void value_compare(){
        ImageView arrow = new ImageView(this);


        if(left_value_1 !=0 && right_value_1 !=0){
                if(left_value_1==right_value_1){
                    arrow.setImageResource(R.drawable.equal);
                    //같은 모양
                }
                else if(left_value_1 > right_value_1){
                    arrow.setImageResource(R.drawable.up_arrow);
                    //화살표 윗모양
                }
                else {
                    arrow.setImageResource(R.drawable.down_arrow);
                    //화살표 아래모양
                }
            }

        if(left_value_2 !=0 && right_value_2 !=0){
            if(left_value_2==right_value_2){
                arrow.setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_2 > right_value_2){
                arrow.setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow.setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }

        if(left_value_3 !=0 && right_value_3 !=0){
            if(left_value_3==right_value_3){
                arrow.setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_3 > right_value_3){
                arrow.setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow.setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }

        if(left_value_4 !=0 && right_value_4 !=0){
            if(left_value_4==right_value_4){
                arrow.setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_4 > right_value_4){
                arrow.setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow.setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }

        if(left_value_5 !=0 && right_value_5 !=0){
            if(left_value_5==right_value_5){
                arrow.setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_5 > right_value_5){
                arrow.setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow.setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }

        if(left_value_6 !=0 && right_value_6 !=0){
            if(left_value_6==right_value_6){
                arrow.setImageResource(R.drawable.equal);
                //같은 모양
            }
            else if(left_value_6 > right_value_6){
                arrow.setImageResource(R.drawable.up_arrow);
                //화살표 윗모양
            }
            else {
                arrow.setImageResource(R.drawable.down_arrow);
                //화살표 아래모양
            }
        }




    }
    public void insertToDatabase(String mem_name,String count) {
         String value_count = count;

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername = barcode_num;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("food_barcode", paramUsername));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://14.63.213.212/main/barcode");
                    Log.d("22qwerqwerq", "insert" + paramUsername);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = null;
                    response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    //서버응답값


                    InputStream in = (InputStream)response.getEntity().getContent();
                    //in.reset();
                    Log.d("661361", "456465");
                    BufferedReader buferedReader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                    Log.d("888888", "456465");
                    String line = null;
                    String result = "";
                    //result += value_count;
                    while ((line = buferedReader.readLine()) != null) {

                        result += line;

                    }
                    Log.d("wehhdfg", result + 456);

                    return result;


                } catch (ClientProtocolException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                parsedData = jsonParserList(result);
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(barcode_num);

    }
    public String[][] jsonParserList(String pRecvServerPage) {

        //Log.i("QQQQ", pRecvServerPage);

        try {
            //value1 -  애너지, 탄수화물 , 지방, 단백질, 나트륨, 콜레스트롤
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONObject a = json.getJSONObject("result");


            //String count = a.getString("count");
            String food_calbo = a.getString("food_calbo");
            String food_stargrade = a.getString("food_stargrade");
            //Log.d("count");
            Log.d("qwe",count);
            if(count=="1"){
                left_Product_name.setText(a.getString("food_name"));
                Left_value1.setText(a.getString("food_energy")+"g");
                Left_value2.setText(a.getString("food_calbo")+"g");
                Left_value3.setText(a.getString("food_fat")+"g");
                Left_value4.setText(a.getString("food_protein")+"g");
                Left_value5.setText(a.getString("food_na")+"g");
                Left_value6.setText(a.getString("food_chol")+"g");

            }
            else if(count=="2"){
                right_Product_name.setText(a.getString("food_name"));
                Right_value1.setText(a.getString("food_energy") + "g");
                Right_value2.setText(a.getString("food_calbo")+"g");
                Right_value3.setText(a.getString("food_fat")+"g");
                Right_value4.setText(a.getString("food_protein")+"g");
                Right_value5.setText(a.getString("food_na")+"g");
                Right_value6.setText(a.getString("food_chol")+"g");
            }


            Log.e("aaaaaaaaa", food_calbo + food_stargrade);

            String qwer = "";
            qwer = json.toString();
            Log.d("dddd", qwer);



            String[][] parseredData = new String[0][0];

            return parseredData;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }









}

