package com.khalej.joud.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_general_user;


import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.realm.Realm;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextView skip,signUp,forgetPassword,signUpFani;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    ProgressDialog progressDialog;
    private contact_general_user contactList;
    private apiinterface_home apiinterface;

    String codee;
    LoginButton loginButton;
    AppCompatButton appCompatButtonRegisterservcies;
    double lat=0.0,lng=0.0;
    Realm realm;
    String lang;
    EditText textInputEditTextpassword,textInputEditTextemail;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        sharedpref = getSharedPreferences("Education",Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        lang=sharedpref.getString("language","").trim();
        if(lang.equals(null)){
            edt.putString("language","ar");
            lang="en";
            edt.apply();
        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_login);
        appCompatButtonRegisterservcies=findViewById(R.id.appCompatButtonRegisterservcies);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        realm = Realm.getDefaultInstance();




        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        edt.putString("lat", String.valueOf(lat));
        edt.putString("lng", String.valueOf(lng));
        edt.apply();


        if(sharedpref.getString("remember","").trim().equals("yes")){
            edt.putFloat("totalprice",0);
            edt.apply();

            startActivity(new Intent(Login.this, MainActivity.class));

            finish();
        }
        skip=findViewById(R.id.skip);

        textInputEditTextemail=findViewById(R.id.textInputEditTextemail);
        textInputEditTextpassword=findViewById(R.id.textInputEditTextpassword);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });
        forgetPassword=findViewById(R.id.forgetPassword);
        signUp=findViewById(R.id.signUp);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Login.this, ForgetPassword.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this, CodeMobile.class);
                intent.putExtra("ty",1);
                startActivity(intent);

            }
        });
        appCompatButtonRegisterservcies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textInputEditTextemail.getText().toString().equals("")||textInputEditTextemail.getText().toString()==null){}
                else{
                    fetchInfo();}
            }
        });
    }

    public void fetchInfo(){
        progressDialog = ProgressDialog.show(Login.this,"جاري تسجيل الدخول","Please wait...",false,false);
        progressDialog.show();
        String email=textInputEditTextemail.getText().toString();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user> call= apiinterface.getcontacts_login(email,
                textInputEditTextpassword.getText().toString());
        call.enqueue(new Callback<contact_general_user>() {
            @Override
            public void onResponse(Call<contact_general_user> call, Response<contact_general_user> response) {
                progressDialog.dismiss();
                if (response.code() == 404) {
                    Toast.makeText(Login.this,"هناك خطأ فى الهاتف او الرقم السري ",Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                }
                if(response.isSuccessful()){

                    contactList = response.body();
                    try {
                        progressDialog.dismiss();
                        edt.putString("id",contactList.getPayload().getUser_info().getId());
                        edt.putString("name",contactList.getPayload().getUser_info().getFull_name());
                        edt.putString("phone",contactList.getPayload().getUser_info().getPhone());
                        edt.putString("address",contactList.getPayload().getUser_info().getEmail());
                        edt.putString("type",contactList.getPayload().getUser_info().getType());
                        edt.putString("token",contactList.getPayload().getToken());
                        edt.putString("image",""+contactList.getPayload().getUser_info().getUserMedia().getLogo());
                        edt.putString("remember","yes");
                        edt.apply();

                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Login.this);
                        dlgAlert.setMessage("تم تسجيل الدخول بنجاح");
                        dlgAlert.setTitle("Joud");
                        dlgAlert.setIcon(R.drawable.logo);

                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                        startActivity(new Intent(Login.this,MainActivity.class));


                    }
                    catch (Exception e){
                        Toast.makeText(Login.this,"هناك خطأ فى الهاتف او الرقم السري /",Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<contact_general_user> call, Throwable t) {
                Toast.makeText(Login.this,"هناك خطأ فى الهاتف او الرقم السري",Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
            }
        });
    }


}