package com.example.sangwoon.rang;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final int ALL_CHECK_BUTTON = 0;

    // private static final int ALL_CHECK_BUTTON = 0;

    private RadioButton[] mRadioBoxs;
    private CheckBox[] mCheckBoxs;
    private Dialog mMainDialog;
    public RadioButton sex_men, sex_women;
    public RadioGroup sex_group;
    public EditText a_add_info_age,a_add_info_weight;
    public int value_add_info_weight;
    public String value_add_info_age;
    public CheckBox check_all,check_one,check_two,check_three,check_four;

    public String mem_age="0",mem_sex="0",mem_weight="0",mem_diabetes="0",mem_obesity="0",mem_highblood="0",mem_hyper="0";



    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    //@InjectView(R.id.link_login) TextView _loginLink;
    @InjectView(R.id.RE_input_password)
    EditText _Re_passwordText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);




        Button add_info_select = (Button) findViewById(R.id.add_info_select);
        add_info_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                sex_group = (RadioGroup)mMainDialog.findViewById(R.id.sex_select);
                sex_men= (RadioButton)mMainDialog.findViewById(R.id.add_info_sex_men);
                sex_women = (RadioButton)mMainDialog.findViewById(R.id.add_info_sex_women);


                a_add_info_age = (EditText)mMainDialog.findViewById(R.id.edit_add_info_age);
                a_add_info_weight =(EditText)mMainDialog.findViewById(R.id.edit_add_info_weight);
                check_all=(CheckBox)mMainDialog.findViewById(R.id.cb_check_all);
                check_one=(CheckBox)mMainDialog.findViewById(R.id.cb_01);
                check_two=(CheckBox)mMainDialog.findViewById(R.id.cb_02);
                check_three=(CheckBox)mMainDialog.findViewById(R.id.cb_03);
                check_four=(CheckBox)mMainDialog.findViewById(R.id.cb_04);




                mRadioBoxs = new RadioButton[]{


                        (RadioButton) mMainDialog.findViewById(R.id.add_info_sex_men),
                        (RadioButton) mMainDialog.findViewById(R.id.add_info_sex_women),


                };

                mCheckBoxs = new CheckBox[]{
                        (CheckBox)mMainDialog.findViewById(R.id.cb_check_all),
                        (CheckBox)mMainDialog.findViewById(R.id.cb_01),
                        (CheckBox)mMainDialog.findViewById(R.id.cb_02),
                        (CheckBox)mMainDialog.findViewById(R.id.cb_03),
                        (CheckBox)mMainDialog.findViewById(R.id.cb_04)

                };


                switch (v.getId()) {
                    case R.id.add_info_select:

                        mMainDialog = createDialog();
                        mMainDialog.show();

                        break;
                }

            }
        });


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();

            }
        });

        //   _loginLink.setOnClickListener(new View.OnClickListener() {
        //      @Override
        //      public void onClick(View v) {
        //         // Finish the registration screen and return to the Login activity
        //         finish();
        //     }
        // });
    }

    private AlertDialog createDialog() {


        final View innerView = getLayoutInflater().inflate(R.layout.add_info_inflate, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("부가정보선택");
        ab.setView(innerView);



        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {



                sex_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {


                        if(checkedId==R.id.add_info_sex_men){
                            mem_sex="0";
                        }
                        if(checkedId==R.id.add_info_sex_women){
                            mem_sex="1";
                        }
                        else{
                            mem_sex="";
                        }

                    }
                });

                mem_diabetes="0";            //당뇨
                mem_obesity="0";            //비만
                mem_highblood="0";          //고혈압
                mem_hyper="0";           //고지혈증

//                if(check_all.isChecked()==true){
//                    mem_diabetes="1";
//                    mem_obesity="1";
//                    mem_highblood="1";
//                    mem_hyper="1";
//
//                }
                if(check_one.isChecked()==true){
                    //당뇨
                    mem_diabetes="1";

                }
                if(check_two.isChecked()==true){
                    //비만
                    mem_obesity="1";
                }
                if(check_three.isChecked()==true){
                    //고혈압
                    mem_highblood="1";
                }
                if(check_four.isChecked()==true){
                    //고지혈증
                    mem_hyper="1";
                }

                mem_age=(a_add_info_age.getText().toString());
                mem_weight=(a_add_info_weight.getText().toString());





                setDismiss(mMainDialog);

            }
        });


        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mMainDialog);
            }
        });

//        mCheckBoxs[ALL_CHECK_BUTTON].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                allChecked(mCheckBoxs, mCheckBoxs[ALL_CHECK_BUTTON].isChecked());
//            }
//        });

        return ab.create();
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


    //=========================================================================================================================================================================

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

       //mem_phone = "123";
        String mem_name = _nameText.getText().toString();
        String mem_email = _emailText.getText().toString();
        String mem_pwd = _passwordText.getText().toString();



        //성공했으면 return "success"
        //회원가입 칸 유효성 검사후에 DB에 사용자 정보 입력

        insertToDatabase(mem_name, mem_email, mem_pwd, mem_age, mem_sex, mem_weight, mem_diabetes, mem_obesity, mem_highblood, mem_hyper);

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);

        progressDialog.setMessage(mem_name);
       // progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        // TODO: Implement your own signup logic here.


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    //android - > php -> db (insert)
    public void insertToDatabase(String mem_name, String mem_email, String mem_pwd, String mem_age,String mem_sex, String mem_weight, String mem_diabetes, String mem_obesity, String mem_highblood, String mem_hyper) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername = params[0];
                String paramEmail = params[1];
                String paramPassword = params[2];
                String parammemage = params[3];
                String parammemesex = params[4];
                String parammemweight = params[5];
                String parammemdiabetes = params[6];
                String parammemobesity = params[7];
                String parammemhighblood = params[8];
                String parammemhyper = params[9];


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mem_name", paramUsername));
                nameValuePairs.add(new BasicNameValuePair("mem_email", paramEmail));
                nameValuePairs.add(new BasicNameValuePair("mem_pwd", paramPassword));

                nameValuePairs.add(new BasicNameValuePair("mem_age", parammemage));
                nameValuePairs.add(new BasicNameValuePair("mem_sex", parammemesex));
                nameValuePairs.add(new BasicNameValuePair("mem_weight", parammemweight));
                nameValuePairs.add(new BasicNameValuePair("mem_diabetes", parammemdiabetes));
                nameValuePairs.add(new BasicNameValuePair("mem_obesity", parammemobesity));
                nameValuePairs.add(new BasicNameValuePair("mem_highblood", parammemhighblood));
                nameValuePairs.add(new BasicNameValuePair("mem_hyper", parammemhyper));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://14.63.213.212:55/signup");
                    Log.d("qwerqwerq", "insert"+paramUsername);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    Log.d("123333", "insert" + paramPassword);
                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    //서버 응답값
                    String value = EntityUtils.toString(response.getEntity());
                    Log.d(TAG,value);



                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        Log.d("qwerqwerq", "insert"+mem_age);
        Log.d("성별", mem_sex+123) ;
        Log.d("몸무게",mem_weight);
        Log.d("당뇨",mem_diabetes);
        Log.d("비만", mem_obesity);
        Log.d("고혈압",mem_highblood);
        Log.d("고지혈증", mem_hyper);

        sendPostReqAsyncTask.execute(mem_name, mem_email, mem_pwd, mem_age, mem_sex, mem_weight, mem_diabetes, mem_obesity, mem_highblood, mem_hyper);
        Log.d("전달값2222 ", mem_name+mem_email+mem_pwd+ mem_age+ mem_sex+ mem_weight+ mem_diabetes+ mem_obesity+ mem_highblood+ mem_hyper);
        Log.d(TAG, "insert complete");


    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String re_password = _Re_passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 2) {
            _nameText.setError("at least 2 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else if (password.contentEquals(re_password) != true) {
            _passwordText.setError("password not match");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


}