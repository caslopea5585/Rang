package com.example.sangwoon.rang;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class nutrient_adapter extends ArrayAdapter<nutrient_compare> {

    //필드 정의하기
    private AppCompatActivity activity;
    int layoutRes;
    ArrayList<nutrient_compare> list;
    LayoutInflater inflater;
    public  int index= 0;
    //생성자 정의 하기
    public nutrient_adapter(AppCompatActivity context, int layoutRes, ArrayList<nutrient_compare> list){
        super(context, layoutRes, list);
        this.activity=context;
        this.layoutRes=layoutRes;
        this.list=list;
        //레이아웃을 전개하기 위한 전개자 얻어오기.
        inflater=(LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return list.size();//자료의 총 갯수를 리턴한다.
    }

    @Override
    public nutrient_compare getItem(int position) {

        return list.get(position);//인자로 넘어오는 인덱스에 해당하는 객체를 리턴한다.
    }

    @Override
    public long getItemId(int position) {

        return position; //중복되지 않는 id 역활을 position 이 할수있다.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        index=position;//지역변수는 리턴하면 사라져서 final로 상수로 만듦.(전연변수로 해도 됨)
        ViewHolder holder;
        if(convertView==null){ //메소드가 처음 호출되면 null 값이 넘어온다.
            //자신이 정의한 레이아웃을 전개한다.
            convertView=inflater.inflate(layoutRes, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
            //holder.ratingBar.getTag(position);
        }
        holder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(holder, position));

        holder.ratingBar.setTag(position);
        holder.ratingBar.setRating(getItem(position).getRatingStar());





      /*  //커스텀 레이아웃의 ImageView 객체 얻어오기.
        ImageView image=(ImageView)convertView.findViewById(R.id.image);
        image.setImageResource(list.get(index).imgRes);
        //커스텀 레이아웃의 TextView 객체 얻어오기(이름)
        TextView text=(TextView)convertView.findViewById(R.id.text);
        text.setTextSize(24);
        text.setText(list.get(index).name);
        //커스텀 레이아웃의 TextView 객체 얻어오기(내용)
        TextView content=(TextView)convertView.findViewById(R.id.content);
        content.setTextSize(16);
        content.setTextColor(Color.DKGRAY);
        content.setText(list.get(index).content);*/





        return convertView;//구성한 각각의 View 를 리턴한다.
    }

    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final ViewHolder holder, final int position) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                nutrient_compare item = getItem(position);
                item.setRatingStar(v);
                Log.i("Adapter", "star: " + v);
            }
        };
    }

    private  class ViewHolder {
        private RatingBar ratingBar;
        private ImageView image;
        private TextView text;
        private TextView content;

        public ViewHolder(View view) {
            ratingBar = (RatingBar) view.findViewById(R.id.rate_img);
            ImageView image=(ImageView) view.findViewById(R.id.image);
            image.setImageResource(list.get(index).imgRes);
            TextView text=(TextView) view.findViewById(R.id.text);
            text.setTextSize(24);
            text.setText(list.get(index).name);
            TextView content=(TextView)view.findViewById(R.id.content);
            content.setTextSize(16);
            content.setTextColor(Color.DKGRAY);
            content.setText(list.get(index).content);




        }
    }

}
