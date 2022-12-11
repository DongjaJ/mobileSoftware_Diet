package com.cource.mobilesoftware_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class PlusFoodActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();

    ArrayList<KcalItem> kcalData = new ArrayList<>();

    DatePickerDialog.OnDateSetListener myDatePicker = (view, year, month, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plus_food_activity);

        //글자 색 일부 변경
        TextView function_text = findViewById(R.id.plus_explain); //텍스트 변수 선언

        String content = function_text.getText().toString(); //텍스트 가져옴.
        SpannableString spannableString = new SpannableString(content); //객체 생성

        String word ="똑똑한 식단관리";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#EC7357")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        function_text.setText(spannableString);

        //날짜 객체 가져옴
        EditText et_Date = findViewById(R.id.Date);
        et_Date.setOnClickListener(v -> new DatePickerDialog(PlusFoodActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        final EditText et_time = findViewById(R.id.Time);
        et_time.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);                //한국시간 : +9
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(PlusFoodActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                String state = "AM";
                // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                if (selectedHour > 12) {
                    selectedHour -= 12;
                    state = "PM";
                }
                // EditText에 출력할 형식 지정
                et_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
            }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        Button popupButton = findViewById(R.id.PopupButton);

        findViewById(R.id.PopupButton).setOnClickListener(view -> {
            final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
            getMenuInflater().inflate(R.menu.meal_main,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.morning)
                    popupButton.setText(R.string.morning);
                else if (menuItem.getItemId() == R.id.lunch)
                    popupButton.setText(R.string.lunch);
                else if(menuItem.getItemId() == R.id.dinner)
                    popupButton.setText(R.string.dinner);
                else
                    popupButton.setText(R.string.snack);
                return false;
            });
            popupMenu.show();
        });

        ImageView imageView = findViewById(R.id.imageView);
        final Bitmap[] img_bitmap = new Bitmap[1];

        ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Glide.with(this)
                            .asBitmap()
                            .load(result.getData().getData())
                            .override(127, 93)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    imageView.setImageBitmap(resource);
                                    img_bitmap[0] = resource;
                                }
                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }
                            });
                });

        findViewById(R.id.input).setOnClickListener(view -> {

            final Bundle bundle = new Bundle();

            String date=((EditText)findViewById(R.id.Date)).getText().toString();
            bundle.putString("date",date);
            String time=((EditText)findViewById(R.id.Time)).getText().toString();
            bundle.putString("time",time);
            String food_category= popupButton.getText().toString();
            bundle.putString("food_category",food_category);
            String food_name = ((EditText)findViewById(R.id.food_name)).getText().toString();
            bundle.putString("food_name",food_name);
            Integer food_cnt = Integer.parseInt(((EditText)findViewById(R.id.food_cnt)).getText().toString());
            bundle.putInt("food_cnt",food_cnt);
            String food_summary = ((EditText)findViewById(R.id.editTextTextMultiLine)).getText().toString();
            bundle.putString("food_summary", food_summary);
            String img_name= date + time + "_"+food_category + ".jpg";

            saveBitmapToJpeg(img_bitmap[0], img_name);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img_bitmap[0].compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
            byte[] byteArray = stream.toByteArray() ;
            bundle.putByteArray("bm", byteArray);

            //kcal data 가져오기
            readKcalData();

            int tmp_kcal = 400;
            tmp_kcal = get_kcal(food_name);


            ContentValues addValues = new ContentValues();
            addValues.put(MyContentProvider.DATE,date);
            addValues.put(MyContentProvider.TIME,time);
            addValues.put(MyContentProvider.CATEGORY,food_category);
            addValues.put(MyContentProvider.NAME,food_name);
            addValues.put(MyContentProvider.CNT,food_cnt);
            addValues.put(MyContentProvider.KCAL, tmp_kcal);
;           addValues.put(MyContentProvider.SUMMERY,food_summary);
            addValues.put(MyContentProvider.BYTE, byteArray);

            try {
                Toast.makeText(getApplicationContext(), "파일 로드 성공", Toast.LENGTH_SHORT).show();

                getContentResolver().insert(MyContentProvider.CONTENT_URI, addValues);
                CustomPopupPlus customDialog = new CustomPopupPlus(PlusFoodActivity.this,bundle);
                customDialog.show();
//                Cursor cursor = db.rawQuery("select * from Diet",null);
//                while(cursor.moveToNext()){
//                    tmp += "id: "+cursor.getInt(0)+"\ndate: "+cursor.getString(1)+"" +
//                            "\ntime: " +cursor.getString(2)+"\nfood_category: "+cursor.getString(3)+
//                            "\nfood_name: " +cursor.getString(4) +"\nfood_cnt: "+cursor.getInt(5)+"\nfood_calory: "+cursor.getInt(6)
//                            +"\nfood_summary: " + cursor.getString(7) +"\nimg_name: " + cursor.getString(8)+"\n";
//                }
//
//                ((EditText) findViewById(R.id.editTextTextMultiLine)).setText(tmp);

            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
            }
        });

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });
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


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = findViewById(R.id.Date);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    public void gotoBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //다른 곳 누르면 키보드 내리는 함수
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //칼로리 가져오는 함수
    private void readKcalData(){
        InputStream is = getResources().openRawResource(R.raw.foods_kcal);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        try{
            while((line = reader.readLine()) != null){
                String[] tokens = line.split(",");

                KcalItem sample = new KcalItem();
                sample.setIndex( Integer.parseInt(tokens[0]));
                sample.setF_kcal( (int)(Float.parseFloat(tokens[1])));
                sample.setF_name(tokens[2]);

                kcalData.add(sample);

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private int get_kcal(String name){
        Random random = new Random();
        for(int i = 0; i < kcalData.size(); i++){
            if(name.equals(kcalData.get(i).getF_name())){
                return kcalData.get(i).getF_kcal();
            }
        }
        return 350 - random.nextInt(200); //임의의 값
    }
}
