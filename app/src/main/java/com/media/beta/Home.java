package com.media.beta;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Home extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        img = (ImageView)findViewById(R.id.idsplash);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load("https://firebasestorage.googleapis.com/v0/b/project-f01c1.appspot.com/o/LogoMakr_6Q2Nbz.png?alt=media&token=8e9966cf-cb3e-4ed8-b3c2-4735dbf171de")
                .into(img);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent m = new Intent(Home.this,MenuUtama.class);
                startActivity(m);

            }
        },SPLASH_TIME_OUT);

    }

}
