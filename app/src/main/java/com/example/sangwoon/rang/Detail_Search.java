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
    String[][] cate_Data={};
    String postion_1;
    Bitmap food_Image1,food_Image2, food_Image3;
    ImageView food_image;
    String category_big_result,category_middle_result,category_small_result,mem_email,return_text;
    String[][] category_big_array={};
    String[][] category_middle_array={};
    String[][] category_small_array={};
    //String return_value="";
    String food_barode_return="";
    TextView list_name,list_calbo,list_fat,list_protein,list_na,list_col,list_energy,list_day_calbo,list_day_fat,list_day_protein,list_day_na,list_day_col,list_day_energy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);


        list_name = (TextView)findViewById(R.id.list_name);
        list_calbo = (TextView)findViewById(R.id.list_calbo);
        list_fat = (TextView)findViewById(R.id.list_fat);
        list_protein = (TextView)findViewById(R.id.list_protein);
        list_na = (TextView)findViewById(R.id.list_na);
        list_col = (TextView)findViewById(R.id.list_col);
        list_energy = (TextView)findViewById(R.id.list_energy);


        list_day_calbo = (TextView)findViewById(R.id.list_day_calbo);
        list_day_fat = (TextView)findViewById(R.id.list_day_fat);
        list_day_protein = (TextView)findViewById(R.id.list_day_protein);
        list_day_na = (TextView)findViewById(R.id.list_day_na);
        list_day_col = (TextView)findViewById(R.id.list_day_col);
        list_day_energy = (TextView)findViewById(R.id.list_day_energy);




        //메인에서 넘어온 카테고리 결과값
        Intent category = getIntent();
        category_big_result = category.getStringExtra("category_big");
        category_middle_result = category.getStringExtra("category_middle");
        category_small_result = category.getStringExtra("category_small");
        mem_email = category.getStringExtra("mem_email");



        Log.d("middle_return ", category_middle_result);
        //넘어온 빅 카테고리 결과값 저장한 배열
        category_big_array = category_big(category_big_result);
        //넘어온 미들 카데고리 결과값 저장한 배열
        category_middle_array = category_middle(category_middle_result);
        //넘어온 스몰 카데고리 결과값 저장한 배열
        //임시 스몰도 미들값으로
        category_small_array = category_small(category_small_result);
        Log.d("small_return ", category_small_result);


        food_image = (ImageView) findViewById(R.id.aoa_image);


        //arraylist 1  === 대분류
        //new ArrayList<String> <> <<가 넘길 값
        arraylist1 = new ArrayList<String>();
        for (int cate_big = 0; cate_big < category_big_array.length; cate_big++) {
            arraylist1.add(String.valueOf(category_big_array[cate_big][1]));
        }
//        arraylist1.add("라면");





    //arraylist2, 3 ====중분류

        arraylist2 = new ArrayList<String>();



        arraylist3 = new ArrayList<String>();
//        arraylist3.add("냉동만두");
//        arraylist3.add("냉동피자");
//        arraylist3.add("냉동치킨");
//        arraylist3.add("냉동닭강정");
//        arraylist3.add("냉동누들");
//        arraylist3.add("냉동산적");
//        arraylist3.add("냉동육전");
//        arraylist3.add("냉동갈비");
//        arraylist3.add("중구");
//        arraylist3.add("부평구");




//        final Integer[] imageID = {R.drawable.ca_2, R.drawable.ca_1, R.drawable.cm_1, R.drawable.hj_1, R.drawable.jm_1,
//                R.drawable.ma_1, R.drawable.s1_1, R.drawable.un_1, R.drawable.ca_3, R.drawable.ca_4, R.drawable.ca_5};
//        final Integer[] imagev = {R.drawable.bomi, R.drawable.nam, R.drawable.ha,
//                R.drawable.unji, R.drawable.naun, R.drawable.cho, R.drawable.cho1, R.drawable.cho2, R.drawable.cho3, R.drawable.cho4};
        final Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
        final ArrayAdapter<String> adspin1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraylist1);


        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adspin1);




        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, final View selectedView, int position, long i) {
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
                            spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    int remove_array_value = arraylist3.size();

                                    if(remove_array_value!=0){
                                        for(int remove=0;remove<remove_array_value;remove++){
                                            arraylist3.remove(0);
                                        }
                                    }

                                    for (int check = 0; check < category_small_array.length; check++) {
                                        Log.d("들어가기전","ㅅㅅㅅㅅ");
                                        //if (adspin1.getItem(position)==(Integer.parseInt(category_middle_array[check][1]))) {


                                        //if(Integer.parseInt(category_small_array[check][2])== position+1) {
                                         if(position==6) {
                                            Log.d("과연널포인터?", String.valueOf(category_small_array[0][1]));
                                            arraylist3.add(String.valueOf(category_small_array[check][1]));
                                            //}
                                        }
                                }
                                    //positon에 해당하는 값을 arraylist에 찾아서 parse_array에서 푸드내임을 빼낸다

                                    final ArrayAdapter<String> adspin3 = new ArrayAdapter<String>(selectedView.getContext(), android.R.layout.simple_spinner_dropdown_item, arraylist3);
                                adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin3.setAdapter(adspin3);
                                    spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                        public void onItemSelected(AdapterView<?> parentView,  View selectedView, int position, long id) {

                                                return_text  = category_small_array[position][1];
                                                food_barode_return = category_small_array[position][0];
                                                Log.d("아니이걸", category_small_array[position][1]);
                                                cate_search(food_barode_return, mem_email);
                                                // aoa_image.setImageResource(imagev[position]);



                                        }

                                        public void onNothingSelected(AdapterView<?> parentView) {

                                        }
                                    });


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            Log.d("다돔","ㅇㅇ");
                        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                intent.putExtra("category_middle_result", category_middle_result);
                startActivity(intent); // 다음 화면으로 넘어간다

            }
        });
    }




        public void cate_search(String search_text,  String mem_email2) {
            class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
                @Override
                protected String doInBackground(String... params) {
                    String paramUsername = food_barode_return;
                    String paramUsername2 = mem_email;
                    Log.d("뭐야값이", paramUsername + paramUsername2);
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("mem_email", paramUsername2));
                    nameValuePairs.add(new BasicNameValuePair("food_barcode", paramUsername));


                    try {

                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(
                                "http://14.63.213.212:55/main/barcode");
                        Log.d("22qwerqwerq", "insert" + paramUsername);
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
                        //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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

                        //return_value = result;
                        return result;


                    } catch (ClientProtocolException e) {

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return "success";
                }

                @Override
                protected void onPostExecute(String result) {
                    Log.d("너의정체는",result);
                   // Log.d("너는?",return_value);
                    cate_Data = jsonParserList2(result);
                    super.onPostExecute(result);

                }
            }
            SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
            Log.d("email_id_check", return_text);
            sendPostReqAsyncTask.execute(food_barode_return,mem_email);

        }
        public String[][] jsonParserList2(String pRecvServerPage) {

            Log.i("너모오냐", pRecvServerPage);
            Thread mThread = new Thread();
            Log.d("넘어오는 값", pRecvServerPage);
            try {


                JSONObject json = new JSONObject(pRecvServerPage);
                // 서버로 부터 넘어온 키값을 넣어줌
                JSONObject a = json.getJSONObject("result");


                String day_food_calbo=a.getString("day_calbo");
                String day_food_fat = a.getString("day_fat");
                String day_food_protein = a.getString("day_protein");
                String day_food_na = a.getString("day_na");
                String day_food_col = a.getString("day_chol");
                String day_food_energy = a.getString("day_energy");


                int diabetes = Integer.parseInt(a.getString("mem_diabetes"));
                int obesity = Integer.parseInt(a.getString("mem_obesity"));
                int highblood = Integer.parseInt(a.getString("mem_highblood"));
                int hyper = Integer.parseInt(a.getString("mem_hyper"));
                int weight = Integer.parseInt(a.getString("mem_weight"));

                int day_calbo  = Integer.parseInt(day_food_calbo);
                int day_fat = Integer.parseInt(day_food_fat);
                int day_protein = Integer.parseInt(day_food_protein);
                int day_na = Integer.parseInt(day_food_na);
                int day_chol = Integer.parseInt(day_food_calbo);
                int day_energy = Integer.parseInt(day_food_energy);


                int value_fat = Integer.parseInt(a.getString("food_fat"));
                int value_protein = Integer.parseInt(a.getString("food_protein"));
                int value_energy = Integer.parseInt(a.getString("food_energy"));
                int value_calbo = Integer.parseInt(a.getString("food_calbo"));
                int value_na = Integer.parseInt(a.getString("food_na"));
                int value_col = Integer.parseInt(a.getString("food_chol"));

                if(diabetes==1){
                    day_energy = weight * 35;
                    day_calbo = day_calbo/10 * 8;
                    day_na = day_na -300;


                }
                if(obesity==1){
                    day_energy = day_energy - 500;

                }
                if(highblood==1){
                    day_na = day_na/3;
                }
                if(hyper==1){
                    day_calbo = day_calbo/10 *6;
                    day_fat = (day_fat/3)*2;
                    day_protein = day_protein /2;
                }


                String food_image_url = a.getString("food_image");

                list_name.setText(a.getString("food_name"));
                list_calbo.setText(a.getString("food_calbo")+"g");
                list_fat.setText(a.getString("food_fat") + "g");
                list_protein.setText(a.getString("food_protein") + "g");
                list_na.setText(a.getString("food_na") + "g");
                list_col.setText(a.getString("food_chol") + "g");
                list_energy.setText(a.getString("food_energy") + "g");


                double calbobo = ((double)value_calbo/day_calbo) * 100;
                String calbo_cal = String.format("%.2f",calbobo);


                double fatfat = ((double)value_fat/day_fat) * 100;
                String fat_cal = String.format("%.2f",fatfat);

                double prote = ((double)value_protein/day_protein) * 100;
                String protein_cal = String.format("%.2f",prote);

                double nana = ((double)value_na/day_na)* 100;
                String na_cal = String.format("%.2f", nana);

                double colcol = ((double)value_col/day_chol)* 100;
                String col_cal = String.format("%.2f", colcol);

                double enen = ((double)value_energy/day_energy)* 100;
                String energy_cal = String.format("%.2f", enen);

                list_day_calbo.setText(calbo_cal+"%");
                list_day_fat.setText(fat_cal+"%");
                list_day_protein.setText(protein_cal+"%");
                list_day_na.setText(na_cal+"%");
                list_day_col.setText(col_cal+"%");
                list_day_energy.setText(energy_cal+"%");



//
//                fat.setMax(day_fat);
//                max_fat.setText(String.valueOf(day_fat));
//                protein.setMax(day_protein);
//                max_protein.setText(String.valueOf(day_protein));
//                energy.setMax(day_energy);
//                max_energy.setText(String.valueOf(day_energy));
//                calbo.setMax(day_calbo);
//                max_calbo.setText(String.valueOf(day_calbo));
//                na.setMax(day_na);
//                max_na.setText(String.valueOf(day_na));
//                col.setMax(day_chol);
//                max_chol.setText(String.valueOf(day_chol));









//                //for(int image_view=0;image_view<3;image_view++){
//                new LoadImage().execute(parseredData[0][0],parseredData[1][0],parseredData[2][0]);
//                //}
//
//
//                JSONObject json2 = new JSONObject(pRecvServerPage);
//                JSONObject a = json2.getJSONObject("person");
//                user_mail = a.getString("mem_email");
//
//                user_id.setText(user_mail);
//
//                Log.d("value",parseredData[0][0]);


                String[][] parseredData = new String[0][0];
                return parseredData;

            } catch (JSONException e) {

                e.printStackTrace();

                return null;

            }

        }



//
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


        //TextView score = (TextView) findViewById(R.id.tv01);


       // tv01.setText(tv02);


    public String[][] category_small(String pRecvServerPage) {

        Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();
        Log.d("넘어오는 값2222", pRecvServerPage);
        try {


            JSONObject json = new JSONObject(pRecvServerPage);
            // 서버로 부터 넘어온 키값을 넣어줌
            JSONArray jArr = json.getJSONArray("result");


            // 받아온 pRecvServerPage를 분석하는 부분

            String[] jsonName = {"food_barcode","food_name","category_middlenum"};
            String[][] category_small_array = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);

                for(int j = 0; j < jsonName.length; j++) {
                    category_small_array[i][j] = json.getString(jsonName[j]);
                }
            }



            Log.d("small", String.valueOf(category_small_array[0][0]) + String.valueOf(category_small_array[0][1]));

            return category_small_array;

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
