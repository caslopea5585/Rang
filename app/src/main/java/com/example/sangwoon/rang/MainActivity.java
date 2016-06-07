package com.example.sangwoon.rang;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class MainActivity extends AppCompatActivity  {

    private DisplayMetrics metrics;
    private LinearLayout ll_mainLayout;
    private LinearLayout ll_menuLayout;
    private FrameLayout.LayoutParams leftMenuLayoutPrams;
    private int leftMenuWidth;
    private static boolean isLeftExpanded;
    private ImageButton bt_left;
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    public int count=0;

    public Button barcode_button;
    TextView user_id;
    String from_login_email;
    String[][] parsedData={};
    String[][] search_list={};
    String category_big="",category_middle="",category_small="";
    RatingBar left_rating,center_rating,right_rating;
    Bitmap bmimg1,bmimg2,bmimg3;
    ImageButton left_product,center_product,right_product;
    String user_mail, search_text_array;
    ArrayList<String>arrayList1;
    Button search_text;
    String text_search_value;
    String search_text_boolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        user_id = (TextView)findViewById(R.id.user_id);

        left_product = (ImageButton)findViewById(R.id.left_product_image);
        center_product = (ImageButton)findViewById(R.id.centert_product_image);
        right_product = (ImageButton)findViewById(R.id.right_product_image);



        category_big();
        category_middle();
        category_small();


        //로그인 성공한 이메일주소 받아오기
        Intent from_login = getIntent();
        from_login_email = from_login.getStringExtra("login_email");
        insertToDatabase(from_login_email);


        Intent search_list_value = getIntent();
        search_text_array = search_list_value.getStringExtra("search_list_value");

        //search_text_array = 데이터 다 가져온 그냥 평문

        //상품명 검색어 리스트 뷰에 넣기
        search_list = search_array(search_text_array);

        //Log.d("배열값",String.valueOf(search_list[0][0]));

        mListView = (ListView) findViewById(R.id.listView);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        arrayList1 = new ArrayList<String>();

         for(int q=0;q<search_list.length;q++){
             arrayList1.add(String.valueOf(search_list[q][0]));
         }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, arrayList1);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById
                (R.id.search_text_value);
        textView.setAdapter(adapter);


        user_id.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View v) {
              Intent user_info_acitivty = new Intent(MainActivity.this,User_info.class);

                user_info_acitivty.putExtra("login_email",user_mail);
                startActivityForResult(user_info_acitivty,0);

              }
          });

                ImageButton barcode_button = (ImageButton) findViewById(R.id.barcode_button);
        barcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent barcode_activity = new Intent(MainActivity.this, BarcodeActivity.class);
                barcode_activity.putExtra("mem_email",from_login_email);
                startActivityForResult(barcode_activity, 0);


            }
        });

        ImageButton compare_button = (ImageButton)findViewById(R.id.compare);
        compare_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compare_activity = new Intent(MainActivity.this, CompareActivity.class);
                compare_activity.putExtra("mem_email",user_mail);
                startActivity(compare_activity);
            }
        });

        ImageButton Slide_menu_button =(ImageButton)findViewById(R.id.slide_menu);
        Slide_menu_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                initSildeMenu();


            }
        });

        ImageButton detail_search = (ImageButton)findViewById(R.id.detail_search);
        detail_search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Detail_search2 = new Intent(MainActivity.this, Detail_Search.class);
                Log.d("대분류 값 제대로?", category_big);
                Detail_search2.putExtra("category_big", category_big);
                Log.d("중뷴류값은?", category_middle);
                Detail_search2.putExtra("category_middle", category_middle);
                Log.d("소분류값은?", category_small);
                Detail_search2.putExtra("category_small", category_small);
                Detail_search2.putExtra("mem_email",from_login_email);
                startActivityForResult(Detail_search2, 0);

            }
        });


        text_search_value= textView.getText().toString();
        search_text = (Button)findViewById(R.id.text_search);
        search_text.setOnClickListener(new Button.OnClickListener(){
            @Override
        public void onClick(View view){
                Intent search_text_intent = new Intent(MainActivity.this, BarcodeActivity.class);
                search_text_boolean = "1";
                search_text_intent.putExtra("search_text",text_search_value);
                search_text_intent.putExtra("mem_email", from_login_email);
                search_text_intent.putExtra("boolean", search_text_boolean);
                startActivityForResult(search_text_intent, 0);
            }

        });








    }


    public void category_small() {
        class SendPostReqAsyncTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void...Void) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                String url = "http://14.63.213.212:55/food/goods";
                String result ="";
                BufferedReader inStream = null;

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpRequest = new HttpGet(url);
                    HttpResponse response = httpClient.execute(httpRequest);
                    inStream = new BufferedReader(
                            new InputStreamReader(
                                    response.getEntity().getContent()));

                    StringBuffer buffer = new StringBuffer("");
                    String line = "";
                    String NL = System.getProperty("line.separator");
                    while ((line = inStream.readLine()) != null) {
                        buffer.append(line + NL);
                    }
                    inStream.close();

                    result = buffer.toString();



                    return result;


                } catch (ClientProtocolException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("전달값", result);
                category_small = result;
                Log.d("small",category_small);
                // parsedData = jsonParserList(result);
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute();

    }


    public void category_middle() {
        class SendPostReqAsyncTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void...Void) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                String url = "http://14.63.213.212:55/food/middlegoods";
                String result ="";
                BufferedReader inStream = null;

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpRequest = new HttpGet(url);
                    HttpResponse response = httpClient.execute(httpRequest);
                    inStream = new BufferedReader(
                            new InputStreamReader(
                                    response.getEntity().getContent()));

                    StringBuffer buffer = new StringBuffer("");
                    String line = "";
                    String NL = System.getProperty("line.separator");
                    while ((line = inStream.readLine()) != null) {
                        buffer.append(line + NL);
                    }
                    inStream.close();

                    result = buffer.toString();



                    return result;


                } catch (ClientProtocolException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("전달값", result);
                category_middle = result;
                Log.d("middleee",category_middle);
                // parsedData = jsonParserList(result);
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute();

    }
    public void category_big() {
        class SendPostReqAsyncTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void...Void) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                String url = "http://14.63.213.212:55/food/biglist";
                String result ="";
                BufferedReader inStream = null;

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpRequest = new HttpGet(url);
                    HttpResponse response = httpClient.execute(httpRequest);
                    inStream = new BufferedReader(
                            new InputStreamReader(
                                    response.getEntity().getContent()));

                    StringBuffer buffer = new StringBuffer("");
                    String line = "";
                    String NL = System.getProperty("line.separator");
                    while ((line = inStream.readLine()) != null) {
                        buffer.append(line + NL);
                    }
                    inStream.close();

                    result = buffer.toString();



                    return result;


                } catch (ClientProtocolException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("전달값 대분류", result);
                category_big = result;
               // parsedData = jsonParserList(result);
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute();

    }





    //재욱이 소스


    public View newView(Context context, Cursor cursor, ViewGroup parent){
        final LayoutInflater inflater = LayoutInflater.from(context);
        final TextView view = (TextView) inflater.inflate(
                android.R.layout.simple_dropdown_item_1line, parent, false);
        view.setText(cursor.getString(1));
        return view;
    }
    public void bindView(View view, Context context, Cursor cursor){
        ((TextView) view).setText(cursor.getString(1));
    }
    public String convertToString(Cursor cursor){
        return cursor.getString(1);
    }

    public final static String EXTRA_MESSAGE = "unikys.todo.MESSAGE";

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.search_text_value);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }
    public String[][] search_array(String pRecvServerPage) {

        Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();
        Log.d("넘어오는 값2222", pRecvServerPage);
        try {


            JSONObject json = new JSONObject(pRecvServerPage);
            // 서버로 부터 넘어온 키값을 넣어줌
            JSONArray jArr = json.getJSONArray("result");


            // 받아온 pRecvServerPage를 분석하는 부분

            String[] jsonName = {"food_name"};
            String[][] search_list = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);

                for(int j = 0; j < jsonName.length; j++) {
                    search_list[i][j] = json.getString(jsonName[j]);
                }
            }



            Log.d("test", String.valueOf(search_list[0][0]) + String.valueOf(search_list[1][0]));
           // arrayList1.add("가나다");
            return search_list;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }
    public void insertToDatabase(String mem_name) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername = from_login_email;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mem_email", paramUsername));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://14.63.213.212:55/main");
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
        Log.d("email_id_check", from_login_email);
        sendPostReqAsyncTask.execute(from_login_email);

    }
    public String[][] jsonParserList(String pRecvServerPage) {

        Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();
        Log.d("넘어오는 값", pRecvServerPage);
        try {


            JSONObject json = new JSONObject(pRecvServerPage);
            // 서버로 부터 넘어온 키값을 넣어줌
            JSONArray jArr = json.getJSONArray("food");


            // 받아온 pRecvServerPage를 분석하는 부분

            String[] jsonName = {"food_image", "food_barcode","food_name"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);

                for(int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }




            //for(int image_view=0;image_view<3;image_view++){
                new LoadImage().execute(parseredData[0][0],parseredData[1][0],parseredData[2][0]);
            //}


            JSONObject json2 = new JSONObject(pRecvServerPage);
            JSONObject a = json2.getJSONObject("person");
            user_mail = a.getString("mem_email");

            user_id.setText(user_mail);

            Log.d("value",parseredData[0][0]);



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
                bmimg1 = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
                bmimg2 = BitmapFactory.decodeStream((InputStream) new URL(args[1]).getContent());
                bmimg3 = BitmapFactory.decodeStream((InputStream) new URL(args[2]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }


            return bmimg1;
        }

        protected void onPostExecute(Bitmap image) {
            //Log.d("dd", "ddd" + bmimg);

            left_product.setImageBitmap(bmimg1);
            center_product.setImageBitmap(bmimg2);
            right_product.setImageBitmap(bmimg3);

        }
    }



    private void initSildeMenu() {



        if(count!=1) {
            mAdapter.addItem(getResources().getDrawable(R.drawable.history_button),
                    "검색기록보기",
                    "2016-06-07");
            mAdapter.addItem(getResources().getDrawable(R.drawable.search_barcode),
                    "바코드검색",
                    "2016-06-07");
            mAdapter.addItem(getResources().getDrawable(R.drawable.product_compare),
                    "상품비교하기",
                    "2016-06-07");
            mAdapter.addItem(getResources().getDrawable(R.drawable.user_info),
                    "개인정보",
                    "2016-06-07");
            count++;
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                switch (position){

                    case 0:
                        Intent search_history = new Intent(MainActivity.this,Search_History.class);
                        startActivity(search_history);
                        break;
                    case 1:
                        Intent barcode_activity = new Intent(MainActivity.this, BarcodeActivity.class);
                        startActivity(barcode_activity);
                        break;
                    case 2:
                        Intent compare_activity = new Intent(MainActivity.this, CompareActivity.class);
                        startActivity(compare_activity);
                        break;
                    case 3:
                        Intent User_info = new Intent(MainActivity.this, User_info.class);
                        startActivity(User_info);
                        break;






                }


                ListData mData = mAdapter.mListData.get(position);
                Toast.makeText(MainActivity.this, mData.mTitle, Toast.LENGTH_SHORT).show();
            }
        });





        // init left menu width
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        leftMenuWidth = (int) ((metrics.widthPixels) * 0.75);

        // init main view
        ll_mainLayout = (LinearLayout) findViewById(R.id.ll_mainlayout);

        // init left menu
        ll_menuLayout = (LinearLayout) findViewById(R.id.ll_menuLayout);
        leftMenuLayoutPrams = (FrameLayout.LayoutParams) ll_menuLayout
                .getLayoutParams();
        leftMenuLayoutPrams.width = leftMenuWidth;
        ll_menuLayout.setLayoutParams(leftMenuLayoutPrams);

        // init ui
        bt_left = (ImageButton) findViewById(R.id.slide_menu);

        menuLeftSlideAnimationToggle();
    }
    /**
     * left menu toggle
     */
    private void menuLeftSlideAnimationToggle() {

        if (!isLeftExpanded) {

            isLeftExpanded = true;

            // Expand
            new OpenAnimation(ll_mainLayout, leftMenuWidth,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);
            // disable all of main view
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, false);

            // enable empty view

            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.VISIBLE);

            findViewById(R.id.ll_empty).setEnabled(true);
            findViewById(R.id.ll_empty).setOnTouchListener(
                    new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View arg0, MotionEvent arg1) {
                            menuLeftSlideAnimationToggle();
                            return true;
                        }
                    });


        } else {
            isLeftExpanded = false;

            // close
            new CloseAnimation(ll_mainLayout, leftMenuWidth,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
            // enable all of main view
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, true);

            // disable empty view
            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.GONE);
            findViewById(R.id.ll_empty).setEnabled(false);

        }
    }
    public static void enableDisableViewGroup(ViewGroup viewGroup,
                                              boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (view.getId() != R.id.slide_menu) {
                view.setEnabled(enabled);
                if (view instanceof ViewGroup) {
                    enableDisableViewGroup((ViewGroup) view, enabled);
                }
            }
        }
    }


    public class ViewHolder {
        public ImageView mIcon;

        public TextView mText;

        public TextView mDate;
    }

    private class ListViewAdapter extends BaseAdapter {
        public Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(Drawable icon, String mTitle, String mDate){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;
            addInfo.mDate = mDate;

            mListData.add(addInfo);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.main_listview, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.mImage);
                holder.mText = (TextView) convertView.findViewById(R.id.mText);
                holder.mDate = (TextView) convertView.findViewById(R.id.mDate);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);
            }else{
                holder.mIcon.setVisibility(View.GONE);
            }

            holder.mText.setText(mData.mTitle);
            holder.mDate.setText(mData.mDate);

            return convertView;
        }





    }












}



