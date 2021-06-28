package com.khalej.joud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khalej.joud.R;

public class SelectLanguage extends AppCompatActivity {
   Button button_next;
   LinearLayout arabic,english;
   String lang;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
   TextView arabicT,englishT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        button_next=findViewById(R.id.button_next);
        arabic=findViewById(R.id.arabic);
        english=findViewById(R.id.english);
        arabicT=findViewById(R.id.arabicT);
        englishT=findViewById(R.id.englishT);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                english.setBackgroundResource(R.drawable.backshapeempty);
                arabic.setBackgroundResource(R.drawable.backshapeempty2);
                arabicT.setTextColor(getResources().getColor(R.color.white));
                englishT.setTextColor(getResources().getColor(R.color.orangeColor));
                 lang="ar";
            }
        });
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                english.setBackgroundResource(R.drawable.backshapeempty2);
                arabic.setBackgroundResource(R.drawable.backshapeempty);
                englishT.setTextColor(getResources().getColor(R.color.white));
                arabicT.setTextColor(getResources().getColor(R.color.orangeColor));
                lang="en";
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lang.equals("en")){
                    edt.putString("language","en");
                    edt.apply();
                }else{
                    edt.putString("language","ar");
                    edt.apply();
                }
                startActivity(new Intent(SelectLanguage.this, Login.class));
            }
        });
    }
}
