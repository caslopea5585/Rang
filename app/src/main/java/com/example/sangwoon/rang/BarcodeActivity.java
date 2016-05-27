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


    String barcode_num,food_name,food_name_getvalue,test,load_value,line;
    String qwer[]={"17"};
    String[][] parsedData={};
    String pRecvServerPage="";
    int food_calbo,food_fat,food_protein,food_na,food_col,food_energy;
    TextView food_calbo_getvalue;
    InputStreamReader sr = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
       // new IntentIntegrator(BarcodeActivity.this).initiateScan();




        ImageView[] food_image = new ImageView[1] ;
        food_image[0] = (ImageView)findViewById(R.id.value01_arrow);
        barcode_num="8801043016049";
        insertToDatabase(barcode_num);



        TextView food_name_getvalue = (TextView)findViewById(R.id.food_name);
        food_name_getvalue.setText("0");
        food_name = food_name_getvalue.getText().toString();

        food_calbo_getvalue = (TextView)findViewById(R.id.food_calbo);

        food_calbo_getvalue.setText("0");
        //qwer[0]="10";
        test= qwer[0];

        food_calbo_getvalue.setText(test);

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

    public void insertToDatabase(String mem_name) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername = "8801043016049";



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

                    //String value = EntityUtils.toString(response.getEntity());
                    //Log.d("2222123123", "ddd" + value);
                    //load_value = value;

                    Log.d("123123121", "456465");

                    InputStream in = (InputStream)response.getEntity().getContent();
                    //in.reset();
                    Log.d("661361", "456465");
                    BufferedReader buferedReader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                    Log.d("888888", "456465");
                    String line = null;
                    String result = "";

                    //Log.d("khkkh",result);
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

            food_calbo_getvalue.setText(   food_stargrade);
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

            Product_name.setText(result.getContents());

        }

    }



}





