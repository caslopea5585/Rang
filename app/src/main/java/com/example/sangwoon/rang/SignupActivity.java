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


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final int ALL_CHECK_BUTTON = 0;

    // private static final int ALL_CHECK_BUTTON = 0;

    private RadioButton[] mRadioBoxs;
    private CheckBox[] mCheckBoxs;
    private Dialog mMainDialog;
    public RadioButton sex_men, sex_women;
    public RadioGroup sex_group ;
    public EditText a_add_info_age,a_add_info_weight;
    public int value_add_info_age,value_add_info_weight;
    public CheckBox check_all,check_one,check_two,check_three,check_four;
    public int check_value_one,check_value_two,check_value_three,check_value_four;
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
        sex_men = (RadioButton) findViewById(R.id.add_info_sex_men);
        sex_women = (RadioButton) findViewById(R.id.add_info_sex_women);
        sex_group = (RadioGroup)findViewById(R.id.sex_select);



        a_add_info_age = (EditText)innerView.findViewById(R.id.edit_add_info_age);
       a_add_info_weight =(EditText)innerView.findViewById(R.id.edit_add_info_weight);
        check_all=(CheckBox)innerView.findViewById(R.id.cb_check_all);
        check_one=(CheckBox)innerView.findViewById(R.id.cb_01);
        check_two=(CheckBox)innerView.findViewById(R.id.cb_02);
        check_three=(CheckBox)innerView.findViewById(R.id.cb_03);
        check_four=(CheckBox)innerView.findViewById(R.id.cb_04);

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


                (RadioButton) innerView.findViewById(R.id.add_info_sex_men),
                (RadioButton) innerView.findViewById(R.id.add_info_sex_women),


        };

        mCheckBoxs = new CheckBox[]{
                (CheckBox)innerView.findViewById(R.id.cb_check_all),
                (CheckBox)innerView.findViewById(R.id.cb_01),
                (CheckBox)innerView.findViewById(R.id.cb_02),
                (CheckBox)innerView.findViewById(R.id.cb_03),
                (CheckBox)innerView.findViewById(R.id.cb_04)

        };



        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                value_add_info_age=Integer.parseInt(a_add_info_age.getText().toString());
                value_add_info_weight=Integer.parseInt(a_add_info_weight.getText().toString());


                setDismiss(mMainDialog);

            }
        });


        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mMainDialog);
            }
        });

        mCheckBoxs[ALL_CHECK_BUTTON].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allChecked(mCheckBoxs, mCheckBoxs[ALL_CHECK_BUTTON].isChecked());
            }
        });

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

        String mem_phone = "123";
        String mem_name = _nameText.getText().toString();
        String mem_email = _emailText.getText().toString();
        String mem_pwd = _passwordText.getText().toString();
        String re_password = _Re_passwordText.getText().toString();

        //성공했으면 return "success"
        //회원가입 칸 유효성 검사후에 DB에 사용자 정보 입력

        insertToDatabase(mem_phone, mem_name, mem_pwd, mem_email);

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
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
    public void insertToDatabase(String mem_phone, String mem_name, String mem_email, String mem_password) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String parammemphone = params[0];
                String paramUsername = params[1];
                String paramEmail = params[2];
                String paramPassword = params[3];


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mem_phone", parammemphone));
                nameValuePairs.add(new BasicNameValuePair("mem_name", paramUsername));
                nameValuePairs.add(new BasicNameValuePair("mem_pwd", paramEmail));
                nameValuePairs.add(new BasicNameValuePair("mem_email", paramPassword));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://14.63.213.212/signup");
                    Log.d(TAG, "insert");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();

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
        sendPostReqAsyncTask.execute(mem_name, mem_name, mem_password, mem_email);
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