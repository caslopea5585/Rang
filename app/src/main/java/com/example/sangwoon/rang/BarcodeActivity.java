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


public class BarcodeActivity extends AppCompatActivity {


    String barcode_num="";
    String qwer[]={"17"};
    String[][] parsedData={};
    String pRecvServerPage="";
    int food_calbo,food_fat,food_protein,food_na,food_col,food_energy;
    TextView food_calbo_getvalue,food_name_getvalue,food_fat_getvalue,food_protein_getvalue,food_na_getvalue,food_col_getvalue,food_energy_getvalue;
    InputStreamReader sr = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
       new IntentIntegrator(BarcodeActivity.this).initiateScan();


        ImageView[] food_image = new ImageView[1] ;
        food_image[0] = (ImageView)findViewById(R.id.value01_arrow);
        //barcode_num="8801043016049";
        Log.d("eee", barcode_num + 456);


        food_name_getvalue = (TextView)findViewById(R.id.food_name);
        food_name_getvalue.setText("0");

        food_calbo_getvalue = (TextView)findViewById(R.id.food_calbo);
        food_calbo_getvalue.setText("0");

        food_fat_getvalue = (TextView)findViewById(R.id.food_fat);
        food_fat_getvalue.setText("0");

        food_protein_getvalue = (TextView)findViewById(R.id.food_protein);
        food_protein_getvalue.setText("0");

        food_na_getvalue = (TextView)findViewById(R.id.food_na);
        food_na_getvalue.setText("0");

        food_col_getvalue = (TextView)findViewById(R.id.food_col);
        food_col_getvalue.setText("0");

        food_energy_getvalue = (TextView)findViewById(R.id.food_energy);
        food_energy_getvalue.setText("0");


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

    public void insertToDatabase(String mem_name) {
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

            JSONObject json = new JSONObject(pRecvServerPage);
            JSONObject a = json.getJSONObject("result");


            String food_calbo = a.getString("food_calbo");
            String food_stargrade = a.getString("food_stargrade");


            food_name_getvalue.setText(a.getString("food_name"));
            food_calbo_getvalue.setText(a.getString("food_calbo")+"g");
            food_fat_getvalue.setText(a.getString("food_fat")+"g");
            food_protein_getvalue.setText(a.getString("food_protein")+"g");
            food_na_getvalue.setText(a.getString("food_na")+"g");
            food_col_getvalue.setText(a.getString("food_chol")+"g");
            food_energy_getvalue.setText(a.getString("food_energy")+"g");


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


            barcode_num = result.getContents();
            insertToDatabase(barcode_num);
        }

    }



}





