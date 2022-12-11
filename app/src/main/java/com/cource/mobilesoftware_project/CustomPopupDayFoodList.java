package com.cource.mobilesoftware_project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomPopupDayFoodList extends Dialog {

    private String TAG = CustomPopupDayFoodList.class.getSimpleName();

    private ImageButton shutdownClick_2;

    private ListView listView = null;
    private CustomPopupDayFoodList.ListViewAdapter adapter = null;

    private CustomPopupPlus customPopupPlus;

    @SuppressLint({"MissingInflatedId", "DefaultLocale"})
    public CustomPopupDayFoodList(@NonNull Context context, int year, int month, int dayOfMonth) {
        super(context);
        setContentView(R.layout.custom_popup_dayfood_list);

        shutdownClick_2 = findViewById(R.id.back_popup_2);
        shutdownClick_2.setOnClickListener(v -> dismiss());

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ListViewAdapter();

//        adapter.addItem(new Fooditem("20220928", "연어덮밥", 420, R.drawable.image_ex_salmon));
//        adapter.addItem(new Fooditem("20221006", "닭한마리", 350, R.drawable.image_ex_chicken));
//        adapter.addItem(new Fooditem("20221014", "햄버거", 386, R.drawable.image_ex_burger));
//        adapter.addItem(new Fooditem("20221108", "샤브샤브", 538, R.drawable.image_ex_shabu));
//        adapter.addItem(new Fooditem("20221115", "떡볶이", 640, R.drawable.image_ex_ddeock));


        //리스트뷰에 Adapter 설정
        listView.setAdapter(adapter);

        WindowManager.LayoutParams params = super.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView dates = (TextView) findViewById(R.id.textView5);
        dates.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));

    }
    public class ListViewAdapter extends BaseAdapter {
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
            TextView list_kcal = (TextView) convertView.findViewById(R.id.list_kcal);
            ImageView list_imageView = (ImageView) convertView.findViewById(R.id.list_imageView);

            list_dates.setText(fooditem.getDates());
            list_name.setText(fooditem.getName());
            list_kcal.setText(fooditem.getKcal());
//            list_imageView.setImageResource(fooditem.getImageID());
            Log.d(TAG, "getView() - [ "+position+" ] "+fooditem.getName());

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)  {
//                    customPopupPlus = new CustomPopupPlus(context, new Bundle());
//                    customPopupPlus.show();
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

}
