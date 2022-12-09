package com.cource.mobilesoftware_project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

        DietDBManager dbManager = new DietDBManager(this);
        SQLiteDatabase db = dbManager.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Diet",null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                adapter.addItem(new Fooditem(cursor.getString(1), cursor.getString(4), 320, cursor.getString(8)));
            }
        }
        else{
            Context context = null;
            Toast toast = Toast.makeText(context, "Something happens.", Toast.LENGTH_LONG);
            toast.show();
        }


//                    tmp += "id: "+cursor.getInt(0)+"\ndate: "+cursor.getString(1)+"" +
//                            "\ntime: " +cursor.getString(2)+"\nfood_category: "+cursor.getString(3)+
//                            "\nfood_name: " +cursor.getString(4) +"\nfood_cnt: "+cursor.getInt(5)+"\nfood_calory: "+cursor.getInt(6)
//                            +"\nfood_summary: " + cursor.getString(7) +"\nimg_name: " + cursor.getString(8)+"\n";
//                }
//
//        adapter.addItem(new Fooditem("20220928", "연어덮밥", "420 kcal", R.drawable.image_ex_salmon));
//        adapter.addItem(new Fooditem("20221006", "닭한마리", "350 kcal", R.drawable.image_ex_chicken));
//        adapter.addItem(new Fooditem("20221014", "햄버거", "386 kcal", R.drawable.image_ex_burger));
//        adapter.addItem(new Fooditem("20221108", "샤브샤브", "538 kcal", R.drawable.image_ex_shabu));
//        adapter.addItem(new Fooditem("20221115", "떡볶이", "640 kcal", R.drawable.image_ex_ddeock));


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
            TextView list_kcal = (TextView) convertView.findViewById(R.id.list_kcal);
            ImageView list_imageView = (ImageView) convertView.findViewById(R.id.list_imageView);

            list_dates.setText(fooditem.getDates());
            list_name.setText(fooditem.getName());
            list_kcal.setText(String.valueOf(fooditem.getKcal()));

            String imgpath = getFilesDir() + "/" + fooditem.getImageID();   // 내부 저장소에 저장되어 있는 이미지 경로
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            list_imageView.setImageBitmap(bm);

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
//        switch (view.getId()){
//            case R.id.button:
//                customPopupPlus = new CustomPopupPlus(this,"다이어로그에 들어갈 내용입니다.");
//                customPopupPlus.show();
//                break;

//        }
    }

    private void saveBitmapToJpeg(Bitmap bitmap, String name) {
        //내부저장소 캐시 경로를 받아옵니다.
        File storage = getFilesDir();
        //저장할 파일 이름
        String fileName = name;
        //storage 에 파일 인스턴스를 생성합니다.
        File tempFile = new File(storage, fileName);
        try {
            // 자동으로 빈 파일을 생성합니다.
            tempFile.createNewFile();
            // 파일을 쓸 수 있는 스트림을 준비합니다.
            FileOutputStream out = new FileOutputStream(tempFile);
            // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            // 스트림 사용후 닫아줍니다.
            out.close();
        } catch (FileNotFoundException e) {
            Log.e("MyTag","FileNotFoundException : " + e.getMessage());
        } catch (IOException e) {
            Log.e("MyTag","IOException : " + e.getMessage());
        }
    }
}
