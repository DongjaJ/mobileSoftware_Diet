package com.cource.mobilesoftware_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CustomPopup extends AppCompatActivity implements OnMapReadyCallback {
    private ImageButton shutdownClick;

    private String latitude;
    private String longitude;
    private String place;
    private String foodname;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_popup_plus);


        shutdownClick = findViewById(R.id.back_popup);
        shutdownClick.setOnClickListener(v -> finish());
        WindowManager.LayoutParams params = super.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Bundle");

        Log.d("test food_name", "CustomPopupPlus: "+bundle.getString("food_name"));

        TextView textView5 = findViewById(R.id.textView5); // food_name
        TextView txt_contents = findViewById(R.id.txt_contents); //food_contents
        ImageView imageView = findViewById(R.id.imageView); // img_name
        TextView food_name = findViewById(R.id.food_name);
        TextView food_cnt = findViewById(R.id.food_cnt);
        TextView time =findViewById(R.id.Time);
        TextView food_category = findViewById(R.id.food_category);
        TextView food_kcal = findViewById(R.id.food_kcal);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        textView5.setText(bundle.getString("food_name","food_name"));
        foodname = bundle.getString("food_name","food_name");
        txt_contents.setText(bundle.getString("food_summary","food_summary"));
        food_name.setText(bundle.getString("food_name","food_name"));
        int tmp= (bundle.getInt("food_cnt",0));
        food_cnt.setText("수량 : " + String.valueOf(tmp));
        TextView date = findViewById(R.id.Date);
        date.setText(bundle.getString("date","date"));
        time.setText((bundle.getString("time","time")));
        food_category.setText(bundle.getString("food_category","food category"));
        tmp= (bundle.getInt("food_kcal",0));
        food_kcal.setText(String.valueOf(tmp) + "Kcal");
        latitude = bundle.getString("latitude","latitude");
        longitude = bundle.getString("longitude", "longitude");
        place = bundle.getString("place", "place");

        byte[] byteArray = bundle.getByteArray("bm");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bitmap);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋

        Button plus = (Button) findViewById(R.id.addPlus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PlusFoodActivity.class);
                startActivity(intent);
            }
        });
        Button show = (Button) findViewById(R.id.showFood);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ShowCalActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onMapReady(final GoogleMap googleMap){
        mMap = googleMap;

        LatLng SEOUL = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title(foodname);
        markerOptions.snippet(place);
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
    }
}
