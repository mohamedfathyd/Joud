package com.khalej.joud.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khalej.joud.R;
import com.khalej.joud.model.apiinterface_home;

import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolbar;
    ImageView card;
    TextView toolbar_title;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    BottomNavigationView navigation;
    String lang;
    ImageView chat;
        Intent intent;
    LinearLayout main,more,myorders,notification;
    int x=0;
    Fragment fragment;
    private apiinterface_home apiinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    sharedpref = getSharedPreferences("Education",Context.MODE_PRIVATE);
    edt = sharedpref.edit();
    lang=sharedpref.getString("language","").trim();
        if(lang.equals(null)){
        edt.putString("language","ar");
        lang="en";
        edt.apply();
    }
    intent=getIntent();
    Locale locale = new Locale(lang);
        Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    getResources().updateConfiguration(config, getResources().getDisplayMetrics());

    setContentView(R.layout.activity_main);

    Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);

        this.setTitle("");
    main=findViewById(R.id.main);
    myorders=findViewById(R.id.myorders);
    notification=findViewById(R.id.notification);
    more=findViewById(R.id.more);

    Fragment fragment = new Main_fragment();
    Bundle bundle2 = new Bundle();
        bundle2.putInt("id",intent.getIntExtra("id",0));
        fragment.setArguments(bundle2);
    loadFragment(fragment);
        if(sharedpref.getString("remember","").equals("yes")){
    }
        main.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment;
            fragment = new Main_fragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("id",intent.getIntExtra("id",0));
            fragment.setArguments(bundle2);
            loadFragment(fragment);

            x=0;
        }
    });
        myorders.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment;
            fragment = new company_fragment();

            loadFragment(fragment);

            x=3;


        }
    });
        notification.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment;
            fragment = new profile_fragment();
            loadFragment(fragment);

            x=2;
        }
    });
        more.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment;
            fragment = new More_fragment();
            loadFragment(fragment);

            x=4;
        }
    });
}


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        if(x==0){
            finish();}
        else{
            Fragment fragment;
            fragment = new Main_fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",intent.getIntExtra("id",0));
            fragment.setArguments(bundle);
            loadFragment(fragment);

            x=0;
        }
    }

}
