package com.ishika.collageapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.ishika.collageapp.R;

import java.util.Objects;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView pdate = findViewById(R.id.pdate);
        TextView ptime = findViewById(R.id.ptime);
        PhotoView photo = findViewById(R.id.photo);

        String image = getIntent().getStringExtra("photo");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");

        pdate.setText(date);
        ptime.setText(time);

        try {
            Glide.with(this).load(image).into(photo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}