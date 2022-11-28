package com.cource.mobilesoftware_project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomPopupPlus extends Dialog {
    private TextView txt_contents;
    private ImageButton shutdownClick;

    @SuppressLint("MissingInflatedId")
    public CustomPopupPlus(@NonNull Context context, String contents) {
        super(context);
        setContentView(R.layout.custom_popup_plus);

        txt_contents = findViewById(R.id.txt_contents);
        txt_contents.setText(contents);
        shutdownClick = findViewById(R.id.back_popup);
        shutdownClick.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        WindowManager.LayoutParams params = super.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
}
