package com.cource.mobilesoftware_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ShowCalActivity extends AppCompatActivity {

    public String readDay = null;
    public String str = null;
    public CalendarView calendarView;
    public TextView diaryTextView;

    String year;
    String month;
    String days;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_cal_activity);


        //글자 색 일부 변경
        TextView function_text = findViewById(R.id.showCal_explain); //텍스트 변수 선언

        String content = function_text.getText().toString(); //텍스트 가져옴.
        SpannableString spannableString = new SpannableString(content); //객체 생성

        String word = "똑똑한 식단관리";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#EC7357")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        function_text.setText(spannableString);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //버튼 안보이게 하기

        Button dayshow = (Button) findViewById(R.id.day_show);
        Button monthlist = (Button) findViewById(R.id.month_list);

        dayshow.setVisibility(View.GONE);
        monthlist.setVisibility(View.GONE);

        //캘린더 설정
        calendarView = findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            @SuppressLint("DefaultLocale")
            public void onSelectedDayChange(@NonNull CalendarView view, int y, int m, int d)
            {
                year = Integer.toString(y);
                month = Integer.toString(m + 1);
                if(d < 10){
                    days = "0"+ Integer.toString(d) ;
                } else{
                    days = Integer.toString(d);
                }

                dayshow.setVisibility(View.VISIBLE);
                monthlist.setVisibility(View.VISIBLE);

                dayshow.setOnClickListener(view1 -> {
                    Intent intent = new Intent(getApplicationContext(), ShowDayListActivity.class);
                    intent.putExtra("MONTH", month);
                    intent.putExtra("YEARS", year);
                    intent.putExtra("DAYS", days);
                    startActivity(intent);
                });

                monthlist.setOnClickListener(view2 -> {
                    Intent intent = new Intent(getApplicationContext(), ShowMonthListActivity.class);
                    intent.putExtra("MONTH", month);
                    intent.putExtra("YEARS", year);
                    startActivity(intent);
                });
            }
        });

    }


    public void goToListView(View view){
        Intent intent = new Intent(this, ShowMonthListActivity.class);
        startActivity(intent);
    }
}