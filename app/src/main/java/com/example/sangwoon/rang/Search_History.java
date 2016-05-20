package com.example.sangwoon.rang;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search_History extends AppCompatActivity {

    private ListView mListView2 = null;
    public ListViewAdapter mAdapter = null;
    public static Context test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_history);
        ImageButton history_remove = (ImageButton)findViewById(R.id.remove_history);
        test=this;
        history_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int remove_count = mAdapter.getCount();
                mAdapter.remove2(remove_count);


            }
        });

        mListView2 = (ListView) findViewById(R.id.history_listview);



        mAdapter = new ListViewAdapter(Search_History.this);
        mListView2.setAdapter(mAdapter);



        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                History_ListData mData = mAdapter.mListData.get(position);
                Toast.makeText(Search_History.this, mData.mTitle, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public class ViewHolder {
        public ImageView mIcon;

        public TextView mText;

        public TextView mDate;

    }
    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<History_ListData> mListData = new ArrayList<History_ListData>();

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
        public long getItemId(int position){



                  return position;
        }

        public void addItem(Drawable icon, String mTitle, String mDate){
            History_ListData addInfo = null;
            addInfo = new History_ListData();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;
            addInfo.mDate = mDate;


            mListData.add(addInfo);
        }



                //((Search_History)Search_History.test).mAdapter.addItem(getResources().getDrawable(R.drawable.supersu),
                 //       "Test",
                  //      "2014-02-04");


        //아이템 삭제
        //히스토리전체삭제

        public void remove2(int position){

            while(position!=0){
                 mListData.remove(position-1);
                Toast.makeText(Search_History.this,"전체 삭제되었습니다.",Toast.LENGTH_LONG).show();

                position--;
           }
            dataChange();


        }
        public void dataChange(){

            Toast.makeText(Search_History.this,"전체 삭제되었습니다.",Toast.LENGTH_LONG).show();
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.history_listview_item, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.mImage);
                holder.mText = (TextView) convertView.findViewById(R.id.mText);
                holder.mDate = (TextView) convertView.findViewById(R.id.mDate);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            History_ListData mData = mListData.get(position);

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
