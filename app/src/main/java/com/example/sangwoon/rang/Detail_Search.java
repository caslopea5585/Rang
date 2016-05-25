package com.example.sangwoon.rang;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Detail_Search extends AppCompatActivity {

    TextView textView1;
    View dialogView;
    TextView tv01;
    String tv02;
    public static float i =0;
    public static String tst;

    private Dialog mainDialog;
    EditText editText;
    TextView score;
    Button button3;
    private RatingBar ratingbar;
    Button button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);

        tv01=(TextView)findViewById(R.id.tv01);

        String[] optionLavala = getResources().getStringArray(R.array.spinnerArray1);
        final Integer[] imageID = {R.drawable.ca_2, R.drawable.ca_1, R.drawable.cm_1, R.drawable.hj_1, R.drawable.jm_1,
                R.drawable.ma_1, R.drawable.s1_1, R.drawable.un_1,};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, optionLavala);


        Spinner obj = (Spinner) findViewById(R.id.spinner1);
        obj.setAdapter(adapter);

        setSpinner(R.id.spinner2, R.array.spinnerArray2,
                android.R.layout.simple_spinner_dropdown_item);

        setSpinner(R.id.spinner3, R.array.spinnerArray3, R.layout.spinner_item);

        getSpinner(R.id.spinner1).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedView, int position, long id) {
                printChecked(selectedView, position);
            }

            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        getSpinner(R.id.spinner2).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedView, int position, long id) {
                printChecked(selectedView, position);
            }

            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


        getSpinner(R.id.spinner3).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedView, int position, long id) {
                ImageView aoa_image = (ImageView) findViewById(R.id.aoa_image);

                aoa_image.setImageResource(imageID[position]);
                printChecked(selectedView, position);

            }

            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });



        TextView score = (TextView)findViewById(R.id.tv01);

        button3= (Button) findViewById(R.id.app_product);
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

        Button button4 = (Button)findViewById(R.id.compare_button);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail_Search.this ,text_compare_Activity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });

        tv01.setText(tv02);




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
            ((TextView) findViewById(R.id.textView2)).setText(resultText);


        }

    }

    public void setSpinner(int objId, int optionLabelId, int listStyle) {
        setSpinner(objId, optionLabelId, -1, listStyle, null);
    }

    public void setSpinner(int objId, int optionLabelId,
                           int optionId, int listStyle, String defaultVal) {
        String[] optionLavala = getResources().getStringArray(optionLabelId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, optionLavala);
        if (listStyle > -1) {
            adapter.setDropDownViewResource(listStyle);

            Spinner obj = (Spinner) findViewById(objId);
            obj.setAdapter(adapter);
            if (defaultVal != null) {
                String[] optiona = getResources().getStringArray(optionId);
                int thei = 0;
                for (int a = 0; a < optiona.length; a++) {
                    if (defaultVal.equals(optiona[a])) {
                        thei = a;
                        break;
                    }
                }
                obj.setSelection(adapter.getPosition(optionLavala[thei]));
            } else {
                obj.setSelection(adapter.getPosition(defaultVal));
            }
        }
    }

    public Spinner getSpinner(int objId) {
        return (Spinner) findViewById(objId);
    }

    public String getSpinnerVal(int objId) {
        return getSpinnerVal(objId, null);
    }

    private String getSpinnerVal(int objId, String[] optiona) {
        String rtn = "";
        Spinner sp = ((Spinner) findViewById(objId));
        if (sp != null) {
            int selectedIndex = sp.getSelectedItemPosition();
            if (optiona == null) {
                rtn = "" + selectedIndex;
            } else {
                if (optiona.length > selectedIndex) {
                    rtn = optiona[selectedIndex];
                }
            }
        }
        return rtn;
    }


    private AlertDialog CreateDialog() {

        final View innerView = getLayoutInflater().inflate(R.layout.vote_dialog, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("상품평가");
        ab.setView(innerView);

        ratingbar = (RatingBar)innerView.findViewById(R.id.rating_bar);
        editText = (EditText)innerView.findViewById(R.id.dlgEdt);

        String symbolvalue;

        symbolvalue = editText.getText().toString();


        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingbar, float rating,
                                        boolean fromUser) {

                if (score != null) {
                    i=ratingbar.getRating();
                    tst=Float.toString(i);

                }


            }
        });


        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                i=ratingbar.getRating();
                tst=Float.toString(i);
                Toast.makeText(Detail_Search.this, tst, Toast.LENGTH_LONG).show();

                dialog.dismiss();

            }


        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which){
                setDismiss(mainDialog);
            }
        });
        return ab.create();
    }

    private void setDismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}