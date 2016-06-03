package com.example.sangwoon.rang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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
    String food_name ="";
    int food_calbo,food_fat,food_protein,food_na,food_col,food_energy;
    TextView food_calbo_getvalue,food_name_getvalue,food_fat_getvalue,food_protein_getvalue,food_na_getvalue,food_col_getvalue,food_energy_getvalue;
    InputStreamReader sr = null;
    ImageView product_image;
    Bitmap bmimg;
    ProgressBar calbo,fat,protein,na,col,energy;
    ImageView calbo_image,fat_image,protein_image,na_image,col_image,energy_image;
    String test="sora@naver.com";
    TextView max_calbo,max_fat,max_protein,max_na,max_chol,max_energy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
       new IntentIntegrator(BarcodeActivity.this).initiateScan();


        calbo= (ProgressBar)findViewById(R.id.calbo_progressbar);
        fat = (ProgressBar)findViewById(R.id.fat_progressbar);
        protein = (ProgressBar)findViewById(R.id.protein_progressbar);
        na =(ProgressBar)findViewById(R.id.na_progressbar);
        col = (ProgressBar)findViewById(R.id.col_progressbar);
        energy = (ProgressBar)findViewById(R.id.energ_progressbar);

        max_calbo = (TextView)findViewById(R.id.max_calbo);
        max_fat = (TextView)findViewById(R.id.max_fat);
        max_protein = (TextView)findViewById(R.id.max_protein);
        max_na = (TextView)findViewById(R.id.max_na);
        max_chol = (TextView)findViewById(R.id.max_chol);
        max_energy = (TextView)findViewById(R.id.max_energy);



        calbo_image = (ImageView)findViewById(R.id.calbo_image);
        fat_image = (ImageView)findViewById(R.id.fat_image);
        protein_image = (ImageView)findViewById(R.id.protein_image);
        na_image = (ImageView)findViewById(R.id.na_image);
        col_image = (ImageView)findViewById(R.id.col_image);
        energy_image = (ImageView)findViewById(R.id.energy_image);





        ImageView[] food_image = new ImageView[1] ;
        food_image[0] = (ImageView)findViewById(R.id.value01_arrow);
        //barcode_num="8801043016049";
        Log.d("eee", barcode_num + 456);

        product_image = (ImageView)findViewById(R.id.imageView);

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


        Button produt_buy = (Button)findViewById(R.id.product_buy);
        produt_buy.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){

                Intent product_buy = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.shopping.naver.com/search/all_search.nhn?query="+food_name+"&frm=MOSCPRO"));
                startActivity(product_buy);
            }
        });


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

    public void insertToDatabase(String mem_name, String test) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername = barcode_num;
                String mem_email="sora@naver.com";
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("food_barcode", paramUsername));
                nameValuePairs.add(new BasicNameValuePair("mem_email", mem_email));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://14.63.213.212:55/main/barcode");
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
                    //Log.d("wehhdfg", result + 456);
                   // buferedReader.close();

                    return result;


                } catch (ClientProtocolException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("Log",result);
                parsedData = jsonParserList(result);
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(barcode_num,"sora@naver.com");

    }
    public String[][] jsonParserList(String pRecvServerPage) {

        Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();

        try {
            Log.d("qwwww",pRecvServerPage);
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONObject a = json.getJSONObject("result");



            String day_food_calbo=a.getString("day_calbo");
            String day_food_fat = a.getString("day_fat");
            String day_food_protein = a.getString("day_protein");
            String day_food_na = a.getString("day_na");
            String day_food_col = a.getString("day_chol");
            String day_food_energy = a.getString("day_energy");


            int day_calbo  = Integer.parseInt(day_food_calbo);
            int day_fat = Integer.parseInt(day_food_fat);
            int day_protein = Integer.parseInt(day_food_protein);
            int day_na = Integer.parseInt(day_food_na);
            int day_chol = Integer.parseInt(day_food_calbo);
            int day_energy = Integer.parseInt(day_food_energy);

//            if(diabetes==1){
//                day_energy = weight * 35;
//                day_calbo = day_calbo * 0.8;
//                day_na = day_na -300;
//
//
//            }
//            if(obesity==1){
//                day_energy = day_energy - 500;
//
//            }
//            if(highblood==1){
//                day_na = day_na/3;
//            }
//            if(highhyper==1){
//                day_calbo = day_calbo *0.6;
//                day_fat = (day_fat/3)*2;
//                day_protein = day_protein /2;
//            }



            String food_image_url = a.getString("food_image");
            food_name = a.getString("food_name");
            food_name_getvalue.setText(a.getString("food_name"));
            food_calbo_getvalue.setText(a.getString("food_calbo")+"g");
            food_fat_getvalue.setText(a.getString("food_fat")+"g");
            food_protein_getvalue.setText(a.getString("food_protein")+"g");
            food_na_getvalue.setText(a.getString("food_na")+"g");
            food_col_getvalue.setText(a.getString("food_chol") + "g");
            food_energy_getvalue.setText(a.getString("food_energy") + "g");



            fat.setMax(day_fat);
            max_fat.setText(day_fat);
            protein.setMax(day_protein);
            max_protein.setText(day_protein);
            energy.setMax(day_energy);
            max_energy.setText(day_energy);
            calbo.setMax(day_calbo);
            max_calbo.setText(day_calbo);
            na.setMax(day_na);
            max_na.setText(day_na);
            col.setMax(day_chol);
            max_chol.setText(day_chol);


            int value_fat = Integer.parseInt(a.getString("food_fat"));
            int value_protein = Integer.parseInt(a.getString("food_protein"));
            int value_energy = Integer.parseInt(a.getString("food_energy"));
            int value_calbo = Integer.parseInt(a.getString("food_calbo"));
            int value_na = Integer.parseInt(a.getString("food_na"));
            int value_col = Integer.parseInt(a.getString("food_chol"));

            calbo.setProgress(value_calbo);
            fat.setProgress(value_fat);
            protein.setProgress(value_protein);
            na.setProgress(value_na);
            col.setProgress(value_col);
            energy.setProgress(value_energy);

            if(value_fat >= (day_calbo/2)){
                fat_image.setImageResource(R.drawable.danger);
            }else if(value_fat < (day_calbo/2) && value_fat>=(day_calbo*0.3)){
                fat_image.setImageResource(R.drawable.warning);
            }

            if(value_protein >= (day_protein/2)){
                protein_image.setImageResource(R.drawable.danger);
            }else if(value_protein < (day_protein/2) && value_protein>=(day_protein*0.3)){
                protein_image.setImageResource(R.drawable.warning);
            }

            if(value_energy >= (day_energy/2)){
                energy_image.setImageResource(R.drawable.danger);
            }else if(value_energy < (day_energy/2) && value_energy>=(day_energy*0.3)){
                energy_image.setImageResource(R.drawable.warning);
            }

            if(value_calbo >= (day_calbo/2)){
                calbo_image.setImageResource(R.drawable.danger);
            }else if(value_calbo < (day_calbo/2) && value_calbo>=(day_calbo*0.3)){
                calbo_image.setImageResource(R.drawable.warning);
            }

            if(value_na >= (day_na/2)){
                na_image.setImageResource(R.drawable.danger);
            }else if(value_na < (day_na/2) && value_na>=(day_na*0.3)){
                na_image.setImageResource(R.drawable.warning);
            }

            if(value_col >= (day_chol/2)){
                col_image.setImageResource(R.drawable.danger);
            }else if(value_col < (day_chol/2) && value_col>=(day_chol*0.3)){
                col_image.setImageResource(R.drawable.warning);
            }


            Log.e("aaaaaaaaa", food_calbo + food_image_url);

            String qwer = "";
            qwer = json.toString();
            Log.d("dddd", qwer);




            String[][] parseredData = new String[0][0];
            new LoadImage().execute(food_image_url);

            return parseredData;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        protected Bitmap doInBackground(String... args) {
            try {
                bmimg = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmimg;
        }

        protected void onPostExecute(Bitmap image) {
            Log.d("dd","ddd"+bmimg);

            product_image.setImageBitmap(image);


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
            insertToDatabase(barcode_num,"sora@naver.com");
        }

    }



}





