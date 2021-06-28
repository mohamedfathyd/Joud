package com.khalej.joud.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.khalej.joud.LocationTrack;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_country;
import com.khalej.joud.model.contact_general_user;
import com.khalej.joud.model.contact_userinfo;
import com.khalej.joud.model.user;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Regester extends AppCompatActivity {
    EditText name,phone,email,password,confirmPassword;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    AppCompatButton regeister;
    private apiinterface_home apiinterface;
    private contact_general_user contactList;
    ProgressDialog progressDialog;

    CallbackManager callbackManager;
    private contact_general_user.contact_user  contact_user;
    private contact_general_user.contact_user_info contact_user_info;
    List<contact_country> contactListCategory= new ArrayList<>();
    Spinner spin,spinCategory;
    double lat=0.0,lng=0.0;
    CheckBox Terms;
    TextView ShowTerms;
    user userData=new user();
    int countryId,categryId;
    Intent intent;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId,code;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog1;
    EditText num;  Dialog dialog;
    String codee="966";
    CountryCodePicker ccp;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        setContentView(R.layout.activity_regester);
        inisialize();
        mAuth=FirebaseAuth.getInstance();
        ccp = findViewById(R.id.ccp);
        codee = ccp.getSelectedCountryCode();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                codee = ccp.getSelectedCountryCode();

            }
        });
        intent=getIntent();
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);

        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        edt.putString("lat", String.valueOf(lat));
        edt.putString("lng", String.valueOf(lng));
        edt.apply();

        spin=findViewById(R.id.spinCountry);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryId=contactListCategory.get(i).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        phone.setText(intent.getStringExtra("phone"));
        regeister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || name.getText().toString() == null) {

                    name.setError("أدخل اسم المستخدم");

                } else if (phone.getText().toString().equals("") || phone.getText().toString() == null) {

                    phone.setError("أدخل رقم الموبيل");

                } else if (password.getText().toString().equals("") || password.getText().toString() == null) {

                    password.setError("أدخل كلمة المرور");

                }
                else if (password.getText().length()<6) {
                       password.setError("أدخل كلمة مرور قوية أكثر من 5 أحرف او ارقام ");
                }
                else if (confirmPassword.getText().toString().equals("") || confirmPassword.getText().toString() == null) {

                    confirmPassword.setError("أدخل  تأكيد كلمة المرور");

                } else if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
                    confirmPassword.setError("كلمة تأكيد مختلفة");

                    confirmPassword.setText("");
                }
//                else if(Terms.isChecked()==false){
//
//                Toast.makeText(Regester.this,"من فضلك قم بلموافقة على الشروط والأحكام",Toast.LENGTH_LONG).show();
//                }
                else {


                      fetchInfo();
                }
            }
        });

        ShowTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   startActivity(new Intent(Regester.this,Terms.class));
            }
        });


    }

    public void fetchInfo() {
        progressDialog = ProgressDialog.show(Regester.this, "جاري انشاء الحساب", "Please wait...", false, false);
        progressDialog.show();
         apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user> call = apiinterface.getcontacts_newaccount(name.getText().toString(),
                password.getText().toString(), email.getText().toString()
                    ,phone.getText().toString(),"02","customer" );
        call.enqueue(new Callback<contact_general_user>() {
            @Override
            public void onResponse(Call<contact_general_user> call, Response<contact_general_user> response) {
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                  // Toast.makeText(Regester.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(Regester.this,"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                progressDialog.dismiss();
                contactList = response.body();

                try{
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

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Regester.this);
                dlgAlert.setMessage("تم تسجيل الدخول بنجاح");
                dlgAlert.setTitle("Joud");
                    dlgAlert.setIcon(R.drawable.logo);

                    dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                startActivity(new Intent(Regester.this,MainActivity.class));}
                catch (Exception e){
                    Toast.makeText(Regester.this, "هناك خطأ حدث الرجاء المحاولة مرة اخري ", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<contact_general_user> call, Throwable t) {
                Toast.makeText(Regester.this,"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح //",Toast.LENGTH_LONG).show();

                progressDialog.dismiss();

            }
        });
    }
    public void inisialize() {
        name = findViewById(R.id.textInputEditTextname);
        phone = findViewById(R.id.textInputEditTextphone);
        email =  findViewById(R.id.textInputEditTextemail);
        password =  findViewById(R.id.textInputEditTextpassword);
        Terms=findViewById(R.id.check);
        ShowTerms=findViewById(R.id.showterms);
        confirmPassword =  findViewById(R.id.textInputEditTextConfirmpassword);
        regeister = (AppCompatButton) findViewById(R.id.appCompatButtonRegisterservcies);

    }


}
