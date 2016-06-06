package com.example.sangwoon.rang;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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

public class Detail_Search extends AppCompatActivity {

    TextView textView1;
    View dialogView;
    TextView tv01;
    String tv02;
    float i = 0;
    String tst;
    String message;

    //private EditText etMessage;
    String test="";
    private Dialog mainDialog;
    EditText editText;
    TextView score;
    Button button3;
    private RatingBar ratingbar;
    Button button4;
    ArrayList<String> arraylist1;
    ArrayList<String> arraylist2;
    ArrayList<String> arraylist3;
    ArrayList<String> arraylist4;
    ArrayList<String> arraylist5;
    ArrayList<String> arraylist6;
    ArrayList<String> arraylist7;
    String [][] parsedData={};
    String postion_1;
    Bitmap food_Image1,food_Image2, food_Image3;
    ImageView food_image;
    String category_big_result,category_middle_result,category_small_result;
    String[][] category_big_array={};
    String[][] category_middle_array={};
    String[][] category_small_array={};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);

        //메인에서 넘어온 카테고리 결과값
        Intent category = getIntent();
        category_big_result = category.getStringExtra("category_big");
        category_middle_result = category.getStringExtra("category_middle");
        category_small_result = category.getStringExtra("category_small");
        Log.d("middle_return ", category_middle_result);
        //넘어온 빅 카테고리 결과값 저장한 배열
        category_big_array = category_big(category_big_result);
        //넘어온 미들 카데고리 결과값 저장한 배열
        category_middle_array = category_middle(category_middle_result);
        //넘어온 스몰 카데고리 결과값 저장한 배열
        category_small_array = category_small(category_small_result);







        food_image = (ImageView)findViewById(R.id.aoa_image);


        //arraylist 1  === 대분류
        //new ArrayList<String> <> <<가 넘길 값
        arraylist1 = new ArrayList<String>();
        for(int cate_big=0 ; cate_big < category_big_array.length;cate_big++){
            arraylist1.add(String.valueOf(category_big_array[cate_big][1]));
        }
//        arraylist1.add("라면");



        //arraylist2, 3 ====중분류

        arraylist2 = new ArrayList<String>();
//        for(int cate_middle=0 ; cate_middle < category_middle_array.length;cate_middle++){
//            if(category_big_array[0][0].equals(category_middle_array[cate_middle][1]))
//                arraylist2.add("값을 선택하세요");
//        }
   //             arraylist2.add("짬뽕");
    //    arraylist2.add("쌀국수");
//        arraylist2.add("강남구");
//        Log.d("들어가기전",String.valueOf(category_big_array[0][1]));
//        int compare_big_middle=0;
//        while(category_big_array[compare_big_middle][1]!=""){
//            if(category_big_array[compare_big_middle][0].equals(category_middle_array[compare_big_middle][1])){
//                for(int cate_middle = 0; cate_middle < category_middle_array.length;cate_middle++){
//                    arraylist2.add(String.valueOf(category_middle_array[cate_middle][0]));
//                }
//            }
//        }


     //   arraylist2.add("라면");
//        arraylist2.add("우동");
//        arraylist2.add("칼국수");
//        arraylist2.add("볶음면");
//        arraylist2.add("짜장면");
//        arraylist2.add("짬뽕");
//        arraylist2.add("쌀국수");
//        arraylist2.add("강남구");
//        arraylist2.add("구로구");
//        arraylist2.add("도봉구");


        arraylist3 = new ArrayList<String>();
        arraylist3.add("냉동만두");
        arraylist3.add("냉동피자");
        arraylist3.add("냉동치킨");
        arraylist3.add("냉동닭강정");
        arraylist3.add("냉동누들");
        arraylist3.add("냉동산적");
        arraylist3.add("냉동육전");
        arraylist3.add("냉동갈비");
        arraylist3.add("중구");
        arraylist3.add("부평구");



//        //arraylist 4
//        // arraylist2 소분류
//
//
//        arraylist4 = new ArrayList<String>();
//        arraylist4.add("사진1");
//        arraylist4.add("사진2");
//        arraylist4.add("사진3");
//        arraylist4.add("사진4");
//        arraylist4.add("사진5");
//        arraylist4.add("사진6");
//        arraylist4.add("사진7");
//        arraylist4.add("사진8");
//        arraylist4.add("사진9");
//        arraylist4.add("사진10");
//
//
//
//        //arraylist2 -2 번째 소분류
//
//        arraylist5 = new ArrayList<String>();
//        arraylist5.add("사진11");
//        arraylist5.add("사진12");
//        arraylist5.add("사진13");
//        arraylist5.add("사진14");
//        arraylist5.add("사진15");
//        arraylist5.add("사진16");
//        arraylist5.add("사진17");
//        arraylist5.add("사진18");
//        arraylist5.add("사진19");
//        arraylist5.add("사진20");
//
//
//        //arraylist3 - 1번째 소분류
//        arraylist6 = new ArrayList<String>();
//        arraylist6.add("사진21");
//        arraylist6.add("사진22");
//        arraylist6.add("사진23");
//        arraylist6.add("사진24");
//        arraylist6.add("사진25");
//        arraylist6.add("사진26");
//        arraylist6.add("사진27");
//        arraylist6.add("사진28");
//        arraylist6.add("사진29");
//        arraylist6.add("사진30");
//
//
//        //arraylist3 - 2번째 소분류
//        arraylist7 = new ArrayList<String>();
//        arraylist7.add("사진31");
//        arraylist7.add("사진32");
//        arraylist7.add("사진33");
//        arraylist7.add("사진34");
//        arraylist7.add("사진35");
//        arraylist7.add("사진36");
//        arraylist7.add("사진37");
//        arraylist7.add("사진38");
//        arraylist7.add("사진39");
//        arraylist7.add("사진40");


        tv01 = (TextView) findViewById(R.id.tv01);


//        final Integer[] imageID = {R.drawable.ca_2, R.drawable.ca_1, R.drawable.cm_1, R.drawable.hj_1, R.drawable.jm_1,
//                R.drawable.ma_1, R.drawable.s1_1, R.drawable.un_1, R.drawable.ca_3, R.drawable.ca_4, R.drawable.ca_5};
//        final Integer[] imagev = {R.drawable.bomi, R.drawable.nam, R.drawable.ha,
//                R.drawable.unji, R.drawable.naun, R.drawable.cho, R.drawable.cho1, R.drawable.cho2, R.drawable.cho3, R.drawable.cho4};
        final Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
        final ArrayAdapter<String> adspin1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraylist1);

        final ArrayAdapter<String> adspin3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraylist3);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adspin1);

        spin3.setAdapter(adspin3);





//        @Override
//        public void onItemClick(AdapterView<?> parent, View selectedView, int position, long id) {
//            printChecked(selectedView, position);
//            for (int check = 0; check < category_middle_array.length; check++) {
//                if (adspin1.getItem(position).equals(Integer.parseInt(category_middle_array[check][0]))) {
//                    arraylist2.add(String.valueOf(category_middle_array[check][0]));
//                }
//            }
//
//        }


        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedView, int position, long i) {
                    Log.d("포문 돌기전", String.valueOf(category_middle_array[0][1]));
                    Log.d("포문 돌기전", String.valueOf(category_middle_array.length));
                    Log.d("포문 돌기전", String.valueOf(position));

                int remove_array_value = arraylist2.size();
                Log.d("size", String.valueOf(remove_array_value));
                if(remove_array_value!=0) {
                    for (int remove = 0; remove < remove_array_value; remove++) {
                        Log.d("for_size", String.valueOf(remove));
                        arraylist2.remove(0);
                    }
                }

                    for (int check = 0; check < category_middle_array.length; check++) {
                        Log.d("들어가기전","ㅅㅅㅅㅅ");
                        //if (adspin1.getItem(position)==(Integer.parseInt(category_middle_array[check][1]))) {


                        if(Integer.parseInt(category_middle_array[check][1])== position+1) {
                            Log.d("포문 도는중", String.valueOf(category_middle_array[0][1]));
                            arraylist2.add(String.valueOf(category_middle_array[check][0]));
                            //}
                        }
                }
                final ArrayAdapter<String> adspin2 = new ArrayAdapter<String>(selectedView.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist2);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(adspin2);
                Log.d("다돔","ㅇㅇ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





//        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedView, int positon, long l) {
//                printChecked(selectedView, positon);
//                if (adspin1.getItem(positon).equals("면")) {
//                    //final ArrayAdapter<String> adspin2 = new ArrayAdapter<String>(selectedView.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist2);
//                    //adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    Spinner2_Adapter adspin2=new Spinner2_Adapter(this, R.layout.content_spinner2__adapter, arraylist2);
//                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
//                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parentView,
//                                                   View selectedView, int position, long id) {
//                            printChecked(selectedView, position);
//                            if (adspin2.getItem(position).equals("라면")) {
//                                final ArrayAdapter<String> adspin3 = new ArrayAdapter<String>(selectedView.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist4);
//                                adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                spin3.setAdapter(adspin3);
//                                spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                                    public void onItemSelected(AdapterView<?> parentView,
//                                                               View selectedView, int position, long id) {
//                                        ImageView aoa_image = (ImageView) findViewById(R.id.aoa_image);
//
//                                       // aoa_image.setImageResource(imagev[position]);
//
//
//
//
//                                        printChecked(selectedView, position);
//
//
//                                    }
//
//                                    public void onNothingSelected(AdapterView<?> parentView) {
//
//                                    }
//                                });
//
//                            } else if (adspin2.getItem(position).equals("우동")) {
//                                final ArrayAdapter<String> adspin3 = new ArrayAdapter<String>(selectedView.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist5);
//                                adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                spin3.setAdapter(adspin3);
//                                spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                                    public void onItemSelected(AdapterView<?> parentView,
//                                                               View selectedView, int position, long id) {
//                                        ImageView aoa_image = (ImageView) findViewById(R.id.aoa_image);
//
//                                       // aoa_image.setImageResource(imageID[position]);
//                                        printChecked(selectedView, position);
//
//                                    }
//
//                                    public void onNothingSelected(AdapterView<?> parentView) {
//
//                                    }
//                                });
//
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parentView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
//                        }
//
//                    });
//
//
//                } else if (adspin1.getItem(positon).equals("냉동식품")) {
//
//                    final ArrayAdapter<String> adspin2 = new ArrayAdapter<String>(selectedView.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist3);
//                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spin2.setAdapter(adspin2);
//                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parentView,
//                                                   View selectedView, int position, long id) {
//                            printChecked(selectedView, position);
//                            if (adspin2.getItem(position).equals("냉동만두")) {
//                                final ArrayAdapter<String> adspin3 = new ArrayAdapter<String>(selectedView.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist6);
//                                adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                spin3.setAdapter(adspin3);
//                                spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                                    public void onItemSelected(AdapterView<?> parentView,
//                                                               View selectedView, int position, long id) {
//                                        ImageView aoa_image = (ImageView) findViewById(R.id.aoa_image);
//
//                                        //aoa_image.setImageResource(imageID[position]);
//                                        printChecked(selectedView, position);
//
//                                    }
//
//                                    public void onNothingSelected(AdapterView<?> parentView) {
//
//                                    }
//                                });
//
//                            } else if (adspin2.getItem(position).equals("냉동피자")) {
//                                final ArrayAdapter<String> adspin3 = new ArrayAdapter<String>(selectedView.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist7);
//                                adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                spin3.setAdapter(adspin3);
//                                spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                                    public void onItemSelected(AdapterView<?> parentView,
//                                                               View selectedView, int position, long id) {
//                                        //insertToDatabase("8801043016059");
//                                        ImageView aoa_image = (ImageView) findViewById(R.id.aoa_image);
//
//                                        //aoa_image.setImageResource(imagev[position]);
//                                        printChecked(selectedView, position);
//
//
//
//                                    }
//
//                                    public void onNothingSelected(AdapterView<?> parentView) {
//
//                                    }
//                                });
//
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parentView) {
//                        }
//                    });
//                }
//
////            }
//
//            public void onNothingSelected(AdapterView<?> parentView) {
//            }
//        });


        TextView score = (TextView) findViewById(R.id.tv01);

        button3 = (Button) findViewById(R.id.app_product);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.app_product:
                        mainDialog = CreateDialog();
                        mainDialog.show();
                        break;

                }
            }
        });

        Button button4 = (Button) findViewById(R.id.compare_button);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail_Search.this, text_compare_Activity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다

            }
        });

        tv01.setText(tv02);


    }
    public String[][] category_small(String pRecvServerPage) {

        Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();
        Log.d("넘어오는 값2222", pRecvServerPage);
        try {


            JSONObject json = new JSONObject(pRecvServerPage);
            // 서버로 부터 넘어온 키값을 넣어줌
            JSONArray jArr = json.getJSONArray("result");


            // 받아온 pRecvServerPage를 분석하는 부분

            String[] jsonName = {"category_name","category_bignum"};
            String[][] category_small_array = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);

                for(int j = 0; j < jsonName.length; j++) {
                    category_small_array[i][j] = json.getString(jsonName[j]);
                }
            }



            Log.d("middle", String.valueOf(category_small_array[0][0]) + String.valueOf(category_small_array[0][1]));

            return category_middle_array;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }

    public String[][] category_middle(String pRecvServerPage) {

        Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();
        Log.d("넘어오는 값2222", pRecvServerPage);
        try {


            JSONObject json = new JSONObject(pRecvServerPage);
            // 서버로 부터 넘어온 키값을 넣어줌
            JSONArray jArr = json.getJSONArray("result");


            // 받아온 pRecvServerPage를 분석하는 부분

            String[] jsonName = {"category_name","category_bignum"};
            String[][] category_middle_array = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);

                for(int j = 0; j < jsonName.length; j++) {
                    category_middle_array[i][j] = json.getString(jsonName[j]);
                }
            }



            Log.d("middle", String.valueOf(category_middle_array[0][0]) + String.valueOf(category_middle_array[0][1]));

            return category_middle_array;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }


    public String[][] category_big(String pRecvServerPage) {

        Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();
        Log.d("넘어오는 값2222", pRecvServerPage);
        try {


            JSONObject json = new JSONObject(pRecvServerPage);
            // 서버로 부터 넘어온 키값을 넣어줌
            JSONArray jArr = json.getJSONArray("result");


            // 받아온 pRecvServerPage를 분석하는 부분

            String[] jsonName = {"category_num","category_name"};
            String[][] category_big_array = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);

                for(int j = 0; j < jsonName.length; j++) {
                    category_big_array[i][j] = json.getString(jsonName[j]);
                }
            }



            Log.d("test222", String.valueOf(category_big_array[0][0]) + String.valueOf(category_big_array[0][1]));

            return category_big_array;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }



    public void printChecked(View v, int position) {
        Spinner sp1 = (Spinner) findViewById(R.id.spinner1);

        Spinner sp2 = (Spinner) findViewById(R.id.spinner2);

        Spinner sp3 = (Spinner) findViewById(R.id.spinner3);

        String resultText = "";
        if (sp1.getSelectedItemPosition() > 0) {
            resultText = (String) sp1.getAdapter().getItem(sp1.getSelectedItemPosition());
        }
        if (sp2.getSelectedItemPosition() > 0) {
            if (!"".equals(resultText))
                resultText += ",";
            resultText += (String) sp2.getAdapter().getItem(sp2.getSelectedItemPosition());
        }
        if (sp3.getSelectedItemPosition() > 0) {
            if (!"".equals(resultText))
                resultText += ",";
            resultText += (String) sp3.getAdapter().getItem(sp3.getSelectedItemPosition());
        }
        if (resultText != "") {
            ((TextView) findViewById(R.id.textView4)).setText(resultText);


        }

    }

    private AlertDialog CreateDialog() {

        final View innerView = getLayoutInflater().inflate(R.layout.vote_dialog, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("상품평가");
        ab.setView(innerView);

        ratingbar = (RatingBar) innerView.findViewById(R.id.rating_bar);
        editText = (EditText) innerView.findViewById(R.id.dlgEdt);


        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingbar, float rating,
                                        boolean fromUser) {

                if (score != null) {
                    i = ratingbar.getRating();
                    // tst = Float.toString(i);

                }


            }
        });


        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                i = ratingbar.getRating();
                tst = Float.toString(i);
                message = editText.getText().toString();
                Log.d("sdasd",message);
                Toast.makeText(Detail_Search.this, tst, Toast.LENGTH_LONG).show();
                insertToDatabase1("paramUsername1", "email", message, i);

                dialog.dismiss();

            }


        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                setDismiss(mainDialog);
            }
        });
        return ab.create();
    }

    private void setDismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public void insertToDatabase1(String mem_email, String food_barcode, String comment_content, float comment_stargrade) {
        class SendPostReqAsyncTask extends AsyncTask< String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername1 = "8801043016049";
                String email = "sjsj@m.com";


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                Log.d("dddd", paramUsername1);
                nameValuePairs.add(new BasicNameValuePair("comment_stargrade", tst));
                nameValuePairs.add(new BasicNameValuePair("comment_content", message));
                nameValuePairs.add(new BasicNameValuePair("mem_email", email));
                nameValuePairs.add(new BasicNameValuePair("food_barcode", paramUsername1));


                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://14.63.213.212:55/food/comment");
                    Log.d("22qwerqwerq", "insert" + paramUsername1);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
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




        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        //  Log.d("email_id_check", from_login_email);
        //  String paramUsername1 = "8801043016049";
        //String email = "sjsj@m.com";
        sendPostReqAsyncTask.execute("sjsj@m.com", "8801043016049", message, "i");

    }


}
