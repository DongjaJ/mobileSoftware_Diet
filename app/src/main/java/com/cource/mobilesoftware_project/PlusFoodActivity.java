package com.cource.mobilesoftware_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PlusFoodActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();

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

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
                if (menuItem.getItemId() == R.id.morning){
                    popupButton.setText(R.string.morning);
                    Toast.makeText(PlusFoodActivity.this, "아침 클릭", Toast.LENGTH_SHORT).show();
                }else if (menuItem.getItemId() == R.id.lunch){
                    popupButton.setText(R.string.lunch);
                    Toast.makeText(PlusFoodActivity.this, "점심 클릭", Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.dinner){
                    popupButton.setText(R.string.dinner);
                    Toast.makeText(PlusFoodActivity.this, "저녁 클릭", Toast.LENGTH_SHORT).show();
                }
                else{
                    popupButton.setText(R.string.snack);
                    Toast.makeText(PlusFoodActivity.this, "간식 클릭", Toast.LENGTH_SHORT).show();
                }

                return false;
            });
            popupMenu.show();
        });


        ImageView imageView = findViewById(R.id.imageView);

        ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> Glide.with(getApplicationContext())
                        .load(result.getData().getData())
                        .override(127, 93)
                        .into(imageView));

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.Date);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void hideKeyboard(View v)
    {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
