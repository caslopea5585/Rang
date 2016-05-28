package com.example.sangwoon.rang;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class User_info extends AppCompatActivity {

    String mem_email_value;
    String[][] parsedData={};

    public RadioButton sex_men, sex_women;
    public RadioGroup sex_group ;
    public EditText a_add_info_age,a_add_info_weight;
    public int value_add_info_age,value_add_info_weight;
    public CheckBox check_all,check_one,check_two,check_three,check_four;
    public int check_value_one,check_value_two,check_value_three,check_value_four;
    private RadioButton[] mRadioBoxs;
    private CheckBox[] mCheckBoxs;
    private Dialog mMainDialog;
    public Button change_password_confirm;
    public TextView new_password2, new_password_confirm2;
    TextView user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        user_id = (TextView)findViewById(R.id.user_id);





        Intent from_main = getIntent();
        mem_email_value = from_main.getStringExtra("user_mail");

        insertToDatabase(mem_email_value);



        Button change_password_value = (Button) findViewById(R.id.change_password);
        change_password_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.change_password:
                        mMainDialog = createDialog();
                        mMainDialog.show();
                        break;
                }

            }
        });

    }



    public void insertToDatabase(String mem_name) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername = mem_email_value;
                //Log.d("이메일값",paramUsername);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mem_email", paramUsername));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://14.63.213.212/main/profile");
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

        sendPostReqAsyncTask.execute(mem_email_value);

    }
    public String[][] jsonParserList(String pRecvServerPage) {

        //Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();
        Log.d("넘어오는 값", pRecvServerPage);
        try {


            JSONObject json = new JSONObject(pRecvServerPage);
            Log.d("미친놈",pRecvServerPage);
            // 서버로 부터 넘어온 키값을 넣어줌
            JSONArray jArr = json.getJSONArray("member");


            // 받아온 pRecvServerPage를 분석하는 부분


            //이름, 나이 , 몸무게, 성별, 당뇨, 비만, 고혈압, 고지혈증

            String[] jsonName = {"mem_name", "mem_age","mem_weight","mem_sex","mem_diabetes","mem_obesity","mem_highblood","mem_hyper"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);

                for(int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }

            user_id.setText(parseredData[0][0]);



//            float left_rating_value = Float.valueOf(parseredData[0][2]);
//            float center_rating_value = Float.valueOf(parseredData[1][2]);
//            float right_rating_value = Float.valueOf(parseredData[2][2]);





            Log.d("value", parseredData[0][0]);



            return parseredData;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }





    private AlertDialog createDialog() {


        final View innerView = getLayoutInflater().inflate(R.layout.change_password, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("비밀번호변경");
        ab.setView(innerView);

        change_password_confirm = (Button)innerView.findViewById(R.id.change_password_confirm);
        new_password2= (TextView)innerView.findViewById(R.id.new_password);
        new_password_confirm2 = (TextView)innerView.findViewById(R.id.new_password_confirm);

        new_password2.setVisibility(View.GONE);
        new_password_confirm2.setVisibility(View.VISIBLE);
        change_password_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.change_password_confirm:
                        //일치 여부 확인하는 post함수

                        new_password2.setVisibility(View.VISIBLE);
                        new_password_confirm2.setVisibility(View.VISIBLE);

                        break;
                }

            }
        });


        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (!validate()) {
                    change_password_validate();
                    return;
                }
                //비밀번호 변경 적용
                setDismiss(mMainDialog);


            }
        });


        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mMainDialog);
            }
        });






        return ab.create();


    }
    public void change_password_validate() {
        Toast.makeText(getBaseContext(), "변경실패", Toast.LENGTH_LONG).show();

        //_signupButton.setEnabled(true);
    }

    private void setDismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
    private void allChecked(CheckBox[] checkboxs, boolean isChecked){
        for(CheckBox chBoxs : mCheckBoxs){
            chBoxs.setChecked(isChecked);
        }
    }

    public void value(){
        sex_men = (RadioButton) findViewById(R.id.add_info_sex_men);
        sex_women = (RadioButton) findViewById(R.id.add_info_sex_women);
        sex_group = (RadioGroup)findViewById(R.id.sex_select);



        a_add_info_age = (EditText)findViewById(R.id.edit_add_info_age);
        a_add_info_weight =(EditText)findViewById(R.id.edit_add_info_weight);
        check_all=(CheckBox)findViewById(R.id.cb_check_all);
        check_one=(CheckBox)findViewById(R.id.cb_01);
        check_two=(CheckBox)findViewById(R.id.cb_02);
        check_three=(CheckBox)findViewById(R.id.cb_03);
        check_four=(CheckBox)findViewById(R.id.cb_04);

        if(check_all.isChecked()){
            check_value_one=1;
            check_value_two=1;
            check_value_three=1;
            check_value_four=1;

        }else if(check_one.isChecked()){
            //당뇨
            check_value_one=1;

        }else if(check_two.isChecked()){
            //비만
            check_value_two=1;
        }else if(check_three.isChecked()){
            //고혈압
            check_value_three=1;
        }else if(check_four.isChecked()){
            //고지혈증
            check_value_four=1;
        }


        mRadioBoxs = new RadioButton[]{

                     (RadioButton)findViewById(R.id.add_info_sex_men),
                     (RadioButton)findViewById(R.id.add_info_sex_women),

        };

        mCheckBoxs = new CheckBox[]{
            (CheckBox)findViewById(R.id.cb_check_all),
                    (CheckBox)findViewById(R.id.cb_01),
                    (CheckBox)findViewById(R.id.cb_02),
                    (CheckBox)findViewById(R.id.cb_03),
                    (CheckBox)findViewById(R.id.cb_04)

        };
    }
    public boolean validate() {
        boolean valid = true;




        String new_password_value = new_password2.getText().toString();
        String new_password_confirm_value = new_password_confirm2.getText().toString();



        if (new_password_value.isEmpty() || new_password_value.length() < 4 || new_password_value.length() > 10) {
            new_password2.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else if (new_password_value.contentEquals(new_password_confirm_value) != true) {
            new_password2.setError("password not match");
            valid = false;
        } else {
            new_password2.setError(null);
        }

        return valid;
    }
}
