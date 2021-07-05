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
    int x=1;
    Fragment fragment;
    private apiinterface_home apiinterface;
    ImageView mainImage,companyImage,profileImage,moreImage;
    TextView mainText,companyText,profileText,moreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
    mainImage=findViewById(R.id.homeImage);
        mainText=findViewById(R.id.homeText);
        companyImage=findViewById(R.id.companyImage);
        companyText=findViewById(R.id.companyText);
        profileImage=findViewById(R.id.profileImage);
        profileText=findViewById(R.id.profileText);
        moreImage=findViewById(R.id.moreImage);
        moreText=findViewById(R.id.moreText);
        setStartStyle();
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
            setStaticStyle();
            final float scale = getResources().getDisplayMetrics().density;
            int dpWidthInPx  = (int) (50 * scale);
            int dpHeightInPx = (int) (50 * scale);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(dpWidthInPx,dpHeightInPx);
            mainImage.setPadding(32,32,32,32);
            mainImage.setLayoutParams(parms);
            mainImage.setBackgroundResource(R.drawable.backactive);
            mainImage.setImageResource(R.drawable.ic_home_white);
            mainText.setVisibility(View.VISIBLE);
            Fragment fragment;
            fragment = new Main_fragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("id",intent.getIntExtra("id",0));
            fragment.setArguments(bundle2);
            loadFragment(fragment);

            x=1;
        }
    });
        myorders.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setStaticStyle();
            final float scale = getResources().getDisplayMetrics().density;
            int dpWidthInPx  = (int) (50 * scale);
            int dpHeightInPx = (int) (50 * scale);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(dpWidthInPx,dpHeightInPx);
            companyImage.setPadding(32,32,32,32);
            companyImage.setLayoutParams(parms);
            companyImage.setBackgroundResource(R.drawable.backactive);
            companyImage.setImageResource(R.drawable.ic_companies_white);
            companyText.setVisibility(View.VISIBLE);
            Fragment fragment;
            fragment = new company_fragment();

            loadFragment(fragment);

            x=3;


        }
    });
        notification.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setStaticStyle();
            final float scale = getResources().getDisplayMetrics().density;
            int dpWidthInPx  = (int) (50 * scale);
            int dpHeightInPx = (int) (50 * scale);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(dpWidthInPx,dpHeightInPx);
            profileImage.setPadding(32,32,32,32);
            profileImage.setLayoutParams(parms);
            profileImage.setBackgroundResource(R.drawable.backactive);
            profileImage.setImageResource(R.drawable.ic_profile_white);
            profileText.setVisibility(View.VISIBLE);
            Fragment fragment;
            fragment = new profile_fragment();
            loadFragment(fragment);

            x=2;
        }
    });
        more.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setStaticStyle();
            final float scale = getResources().getDisplayMetrics().density;
            int dpWidthInPx  = (int) (50 * scale);
            int dpHeightInPx = (int) (50 * scale);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(dpWidthInPx,dpHeightInPx);
            moreImage.setPadding(32,32,32,32);
            moreImage.setLayoutParams(parms);
            moreImage.setBackgroundResource(R.drawable.backactive);
            moreImage.setImageResource(R.drawable.ic_menu_active);
            moreText.setVisibility(View.VISIBLE);
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
        transaction.addToBackStack("tag");
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
   public void setStaticStyle(){
        mainText.setVisibility(View.GONE);
        moreText.setVisibility(View.GONE);
        companyText.setVisibility(View.GONE);
        profileText.setVisibility(View.GONE);
        mainImage.setBackgroundResource(0);
       companyImage.setBackgroundResource(0);
       profileImage.setBackgroundResource(0);
       moreImage.setBackgroundResource(0);
       mainImage.setImageResource(R.drawable.ic_home_inactive);
       companyImage.setImageResource(R.drawable.ic_companies_inactive);
       profileImage.setImageResource(R.drawable.ic_profile_inactive);
       moreImage.setImageResource(R.drawable.ic_menu);
       final float scale = getResources().getDisplayMetrics().density;
       int dpWidthInPx  = (int) (30 * scale);
       int dpHeightInPx = (int) (30 * scale);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(dpWidthInPx,dpHeightInPx);
        mainImage.setPadding(0,0,0,0);
        mainImage.setLayoutParams(parms);
       companyImage.setPadding(0,0,0,0);
       companyImage.setLayoutParams(parms);
       profileImage.setPadding(0,0,0,0);
       profileImage.setLayoutParams(parms);
       moreImage.setPadding(0,0,0,0);
       moreImage.setLayoutParams(parms);
    }
    public void  setStartStyle(){
        setStaticStyle();
        final float scale = getResources().getDisplayMetrics().density;
        int dpWidthInPx  = (int) (50 * scale);
        int dpHeightInPx = (int) (50 * scale);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(dpWidthInPx,dpHeightInPx);
        mainImage.setPadding(32,32,32,32);
        mainImage.setLayoutParams(parms);
        mainImage.setBackgroundResource(R.drawable.backactive);
        mainImage.setImageResource(R.drawable.ic_home_white);
        mainText.setVisibility(View.VISIBLE);
    }
}
