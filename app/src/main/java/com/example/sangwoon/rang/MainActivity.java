package com.example.sangwoon.rang;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mListView = (ListView) findViewById(R.id.listView);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);


        ImageButton barcode_button = (ImageButton)findViewById(R.id.barcode_button);
        barcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent barcode_activity = new Intent(MainActivity.this, BarcodeActivity.class);
                startActivity(barcode_activity);


            }
        });

        ImageButton compare_button = (ImageButton)findViewById(R.id.compare);
        compare_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compare_activity = new Intent(MainActivity.this, CompareActivity.class);
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
    }



    private void initSildeMenu() {



        if(count!=1) {
            mAdapter.addItem(getResources().getDrawable(R.drawable.history_button),
                    "검색기록보기",
                    "2014-02-18");
            mAdapter.addItem(getResources().getDrawable(R.drawable.search_barcode),
                    "바코드검색",
                    "2014-02-01");
            mAdapter.addItem(getResources().getDrawable(R.drawable.product_compare),
                    "상품비교하기",
                    "2014-02-04");
            mAdapter.addItem(getResources().getDrawable(R.drawable.user_info),
                    "개인정보",
                    "2014-02-04");
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
        private Context mContext = null;
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



