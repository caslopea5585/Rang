package com.example.sangwoon.rang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class text_compare_Activity extends AppCompatActivity {

    ListView listView;
    String mid_result_value;
    String[][] mid_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_compare);


        Intent mid_result = getIntent();
        mid_result_value = mid_result.getStringExtra("category_middle_result");
        mid_array(mid_result_value);

        findViewById(R.id.checkBox1).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                printChecked(v);
            }

        });
        findViewById(R.id.checkBox2).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                printChecked(v);
            }

        });
        findViewById(R.id.checkBox3).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                printChecked(v);
            }

        });
        findViewById(R.id.checkBox4).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                printChecked(v);
            }

        });
        findViewById(R.id.checkBox5).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                printChecked(v);
            }

        });
        findViewById(R.id.checkBox6).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                printChecked(v);
            }

        });

        final ToggleButton tb =(ToggleButton)this.findViewById(R.id.toggleButton1);
        tb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb.isChecked()) {
                    tb.setTextColor(Color.RED);
                } else {
                    tb.setTextColor(Color.BLACK);
                }
            }
        });

        final ToggleButton tb1 =(ToggleButton)this.findViewById(R.id.toggleButton2);
        tb1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(tb1.isChecked()){
                    tb1.setTextColor(Color.RED);
                }else{
                    tb1.setTextColor(Color.BLACK);
                }
            }
        });

        final ToggleButton tb2 =(ToggleButton)this.findViewById(R.id.toggleButton3);
        tb2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(tb2.isChecked()){
                    tb2.setTextColor(Color.RED);
                }else{
                    tb2.setTextColor(Color.BLACK);
                }
            }
        });
        //ListView 객체 얻어오기.
        listView=(ListView)findViewById(R.id.listView);
        //아답타에 연결할 배열객체 생성하기
        ArrayList<nutrient_compare> list=new ArrayList<nutrient_compare>();
        list.add(new nutrient_compare(R.drawable.icon01, "김태희","하이~만나서 반가워요!,",1));
        list.add(new nutrient_compare(R.drawable.icon02, "한효주","잘지내시죠^^,1",1));
        list.add(new nutrient_compare(R.drawable.icon03, "윤은혜","대박나세요~~",1));
        list.add(new nutrient_compare(R.drawable.icon04, "신민아","최고에요!! ㅋㅋ",1));
        list.add(new nutrient_compare(R.drawable.icon05, "성유리","어때요??",1));
        list.add(new nutrient_compare(R.drawable.icon06, "이민정","안녕!! 잘지내지...*^^*",1));
        list.add(new nutrient_compare(R.drawable.icon07, "박신혜","뭐해요?? 오빠~~!!",1));
        //자신이 만든 아답타 객체 생성하기.
        nutrient_adapter adapter=new nutrient_adapter(this, R.layout.activity_nutrient_compare, list);
        //ListView 에 아답타 연결하기.
        listView.setAdapter(adapter);


    }

    public String[][] mid_array(String pRecvServerPage) {

        Log.i("QQQQ", pRecvServerPage);
        Thread mThread = new Thread();
        Log.d("넘어오는 값2222", pRecvServerPage);
        try {


            JSONObject json = new JSONObject(pRecvServerPage);
            // 서버로 부터 넘어온 키값을 넣어줌
            JSONArray jArr = json.getJSONArray("result");


            // 받아온 pRecvServerPage를 분석하는 부분
            Log.d("배열전",pRecvServerPage);
            String[] jsonName = {"food_barcode","food_name","food_na","food_fat","food_chol","food_protein","food_image","food_calbo","food_energy","category_middlenum"};
            mid_array = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);

                for(int j = 0; j < jsonName.length; j++) {
                    mid_array[i][j] = json.getString(jsonName[j]);
                }
            }



            Log.d("중분류값?", String.valueOf(mid_array[0][0]) + String.valueOf(mid_array[1][0]));
            // arrayList1.add("가나다");
            return mid_array;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }



    public void printChecked(View v){
        CheckBox ch1 =(CheckBox)findViewById(R.id.checkBox1);
        CheckBox ch2 =(CheckBox)findViewById(R.id.checkBox2);
        CheckBox ch3 =(CheckBox)findViewById(R.id.checkBox3);
        CheckBox ch4 =(CheckBox)findViewById(R.id.checkBox4);
        CheckBox ch5 =(CheckBox)findViewById(R.id.checkBox5);
        CheckBox ch6 =(CheckBox)findViewById(R.id.checkBox6);
        String resultText="";
        if(ch1.isChecked()) {
            resultText = ch1.getText().toString();
        }
        if(ch2.isChecked()) {
            if (!"".equals(resultText))
                resultText += ",";
            resultText += ch2.getText().toString();
        }
        if(ch3.isChecked()) {
            if (!"".equals(resultText))
                resultText += ",";
            resultText += ch3.getText().toString();
        }
        if(ch4.isChecked()) {
            if (!"".equals(resultText))
                resultText += ",";
            resultText += ch4.getText().toString();
        }
        if(ch5.isChecked()) {
            if (!"".equals(resultText))
                resultText += ",";
            resultText += ch5.getText().toString();
        }
        if(ch6.isChecked()) {
            if (!"".equals(resultText))
                resultText += ",";
            resultText += ch6.getText().toString();
        }

        TextView tv=(TextView)findViewById(R.id.textView3);
        tv.setText(resultText);

    }
}
