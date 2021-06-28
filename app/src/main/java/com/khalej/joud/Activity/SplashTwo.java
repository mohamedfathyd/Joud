package com.khalej.joud.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.khalej.joud.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import pl.droidsonroids.gif.GifImageView;

public class SplashTwo extends AppCompatActivity {
    GifImageView i;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        setContentView(R.layout.splashtwo);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alphaa);

        i =  findViewById(R.id.imageView);
        i.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
              startActivity(new Intent(SplashTwo.this, intro_slider.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}