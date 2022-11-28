package com.cource.mobilesoftware_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {
    private String TAG = ShowListActivity.class.getSimpleName();

    private ListView listView = null;
    private ListViewAdapter adapter = null;

    private CustomPopupPlus customPopupPlus;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_activity);

        //글자 색 일부 변경
        TextView function_text = (TextView) findViewById(R.id.showCal_explain); //텍스트 변수 선언

        String content = function_text.getText().toString(); //텍스트 가져옴.
        SpannableString spannableString = new SpannableString(content); //객체 생성

        String word = "똑똑한 식단관리";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#EC7357")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        function_text.setText(spannableString);

        //list view
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListViewAdapter();

        adapter.addItem(new Fooditem("20220928", "연어덮밥", "420 kcal", R.drawable.image_ex_salmon));
        adapter.addItem(new Fooditem("20221006", "닭한마리", "350 kcal", R.drawable.image_ex_chicken));
        adapter.addItem(new Fooditem("20221014", "햄버거", "386 kcal", R.drawable.image_ex_burger));
        adapter.addItem(new Fooditem("20221108", "샤브샤브", "538 kcal", R.drawable.image_ex_shabu));
        adapter.addItem(new Fooditem("20221115", "떡볶이", "640 kcal", R.drawable.image_ex_ddeock));


        //리스트뷰에 Adapter 설정
        listView.setAdapter(adapter);
    }

    public class ListViewAdapter extends BaseAdapter{
        ArrayList<Fooditem> items = new ArrayList<Fooditem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Fooditem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final Fooditem fooditem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_layout, viewGroup, false);

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            TextView list_dates = (TextView) convertView.findViewById(R.id.list_dates);
            TextView list_name = (TextView) convertView.findViewById(R.id.list_name);
            EditText list_kcal = (EditText) convertView.findViewById(R.id.list_kcal);
            ImageView list_imageView = (ImageView) convertView.findViewById(R.id.list_imageView);

            list_dates.setText(fooditem.getDates());
            list_name.setText(fooditem.getName());
            list_kcal.setText(fooditem.getKcal());
            list_imageView.setImageResource(fooditem.getImageID());
            Log.d(TAG, "getView() - [ "+position+" ] "+fooditem.getName());

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, fooditem.getDates()+" - "+fooditem.getName()+" 입니당! ", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

    public void goToCalView(View view){
        Intent intent = new Intent(this, ShowCalActivity.class);
        startActivity(intent);
    }

    public void btnOnclick(View view) {
        switch (view.getId()){
            case R.id.button:
                customPopupPlus = new CustomPopupPlus(this,"다이어로그에 들어갈 내용입니다.");
                customPopupPlus.show();
                break;

        }
    }
}