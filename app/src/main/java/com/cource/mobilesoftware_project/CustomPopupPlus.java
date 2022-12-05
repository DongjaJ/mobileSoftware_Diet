package com.cource.mobilesoftware_project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomPopupPlus extends Dialog {
    private ImageButton shutdownClick;

    @SuppressLint("MissingInflatedId")
    public CustomPopupPlus(@NonNull Context context, Bundle bundle) {
        super(context);
        setContentView(R.layout.custom_popup_plus);

        shutdownClick = findViewById(R.id.back_popup);
        shutdownClick.setOnClickListener(v -> dismiss());
        WindowManager.LayoutParams params = super.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Log.d("test food_name", "CustomPopupPlus: "+bundle.getString("food_name"));

        TextView textView5 = findViewById(R.id.textView5); // food_name
        TextView txt_contents = findViewById(R.id.txt_contents); //food_contents
        ImageView imageView = findViewById(R.id.imageView); // img_name
        TextView food_name = findViewById(R.id.food_name);
        TextView food_cnt = findViewById(R.id.food_cnt);
        TextView time =findViewById(R.id.Time);
        TextView food_category = findViewById(R.id.food_category);

        txt_contents.setText(bundle.getString("food_summary","food_summary"));
        food_name.setText((bundle.getString("food_name","food_name")));
        int tmp= (bundle.getInt("food_cnt",0));
        food_cnt.setText(String.valueOf(tmp));
        TextView date = findViewById(R.id.Date);
        date.setText(bundle.getString("date","date"));
        time.setText((bundle.getString("time","time")));
        food_category.setText(bundle.getString("food_category","food category"));

        byte[] byteArray = bundle.getByteArray("bm");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bitmap);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
    }
}
