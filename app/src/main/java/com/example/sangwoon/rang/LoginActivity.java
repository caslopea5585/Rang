package com.example.sangwoon.rang;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class LoginActivity extends AppCompatActivity {



    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;


    CheckBox Auto_login;
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    String login_value;
    String[][] parsedData={};
    String pRecvServerPage="";
    private  HttpClient mHttpClient;
    String search_list_value="";
    public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
    String test[]={"0"};
    @Override
    public SharedPreferences getPreferences(int mode) {
        return super.getPreferences(mode);
    }

    @Nullable
    

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(this, Splash.class));


       insertToDatabase();

        setting = getSharedPreferences("setting", 0);
        editor = setting.edit();
        if(setting.getBoolean("chk_auto", false)) {
            _emailText.setText(setting.getString("ID", ""));
            _passwordText.setText(setting.getString("PW", ""));
            Auto_login.setChecked(true);
        }





        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {

                //이메일 메인으로 아이디값 받아오기위해
                 login_value = login();
                //while(login_value=="email") {
                    Intent go_menu = new Intent(LoginActivity.this, MainActivity.class);
                    go_menu.putExtra("login_email",login_value);
                    go_menu.putExtra("search_list_value",search_list_value);
                    startActivityForResult(go_menu, 0);
               // }

            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public String login() {

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return "fail";
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
        return email;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically


            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void insertToDatabase() {
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
                Log.d("로그인에서생성", result);

                search_list_value = result;
                parsedData = jsonParserList(result);
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute();

    }
    public String[][] jsonParserList(String pRecvServerPage) {

        Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();
        Log.d("넘어오는 값", pRecvServerPage);
        try {


            JSONObject json = new JSONObject(pRecvServerPage);
            // 서버로 부터 넘어온 키값을 넣어줌
            JSONArray jArr = json.getJSONArray("result");


            // 받아온 pRecvServerPage를 분석하는 부분

            String[] jsonName = {"food_name"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);

                for(int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }



            Log.d("test", String.valueOf(parseredData[0][0]) + String.valueOf(parseredData[1][0]));

            return parseredData;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }


}



