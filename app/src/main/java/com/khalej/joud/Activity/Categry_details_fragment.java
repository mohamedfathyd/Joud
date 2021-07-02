package com.khalej.joud.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce_;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_category;
import com.khalej.joud.model.contact_general;
import com.khalej.joud.model.contact_general_category;
import com.khalej.joud.model.contact_slider;
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Categry_details_fragment extends Fragment {
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_first_annonce_ recyclerAdapter_annonce;
    private RecyclerAdapter_first_annonce recyclerAdapter_first_annonce;
    TextView novalue;
    ArrayList<String> list2;
    private contact_general_category contactList;
    Button cuntipay;
    private apiinterface_home apiinterface;
    private contact_slider contactList_annonce;
    private contact_general contact_general;
    private List<contact_category> contact_category;
    int x = 0;
    int y = 0;
    Switch swtch;
    String id;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    ImageView notification;
    TextView toolbar, name, carddetails, carddiscount, cardprice;
    String[] media;
    Button confirm;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
   double price;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_category, container, false);
        id = getArguments().getString("id");
        media = getArguments().getStringArray("image");
        confirm = view.findViewById(R.id.cunti);
        cuntipay=view.findViewById(R.id.cuntipay);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        notification = view.findViewById(R.id.notification);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);
        toolbar = view.findViewById(R.id.toolbar_title);
        toolbar.setText(getArguments().getString("name"));
        name = view.findViewById(R.id.Cardname);
        name.setText(getArguments().getString("name"));
        price=getArguments().getDouble("price");
       // Toast.makeText(getContext(),price+"",Toast.LENGTH_LONG).show();
        carddetails = view.findViewById(R.id.carddetails);
        carddetails.setText(android.text.Html.fromHtml(getArguments().getString("details")).toString());
        carddiscount = view.findViewById(R.id.textco);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        cardprice = view.findViewById(R.id.price);
        if(sharedpref.getString("language","").trim().equals("ar")){
        cardprice.setText(getArguments().getDouble("price") + "ريال سعودي");
        carddiscount.setText("خصم " + getArguments().getString("discount") + "%");}
        else{
            cardprice.setText(getArguments().getDouble("price") + "R.S");
            carddiscount.setText("Offer " + getArguments().getString("discount") + "%");
        }
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Notification_fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        fetchInfo_annonce();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedpref.getString("remember","").equals("yes")){
                    fetchInfo(false,"");
                   // payment();
                }
                else{
                    Toast.makeText(getContext(),"قم بتسجيل الدخول أولا" ,Toast.LENGTH_LONG).show();
                }
            }
        });
        cuntipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedpref.getString("remember","").equals("yes")){
                    //fetchInfo();
                    fetchInfo(true,"bank_transfer");
                }
                else{
                    startActivity(new Intent(getContext(),attach_file.class));
                    Toast.makeText(getContext(),"قم بتسجيل الدخول أولا" ,Toast.LENGTH_LONG).show();
                }
            }
        });
        try {


            final int counter = 100 * 8000;

            countDownTimer = new CountDownTimer(counter, 8000) {

                public void onTick(long millisUntilFinished) {
                    // Toast.makeText(MainActivity.this , ""+(millisUntilFinished / 1000),Toast.LENGTH_LONG).show();
                    recyclerView2.smoothScrollToPosition(y);
                    y++;
                    if (y > x) {
                        y = 0;
                    }
                }

                public void onFinish() {
                }

            }.start();
        } catch (Exception e) {
        }
        return view;
    }

    public void fetchInfo_annonce() {
        // Toast.makeText(getContext(),media[0]+"",Toast.LENGTH_LONG).show();
        list2 = new ArrayList<String>();
        for (String text : media) {
            list2.add(text);
        }
        try {
            recyclerAdapter_annonce = new RecyclerAdapter_first_annonce_(getActivity(), list2);
            recyclerView2.setAdapter(recyclerAdapter_annonce);


        } catch (Exception e) {
        }

    }

    public void fetchInfo(final boolean payment, final String method){
        progressDialog = ProgressDialog.show(getContext(),"جاري تقديم طلبك","Please wait...",false,false);
        progressDialog.show();
         apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<ResponseBody> call= apiinterface.content_addOrder(headers,sharedpref.getString("id",""),id,payment,method);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
              //  Toast.makeText(getContext(),response.code()+"",Toast.LENGTH_LONG).show();
                if (response.code() == 404) {
                    Toast.makeText(getContext(),"هناك خطأ فى البيانات حاول المحاولة لاحقا ",Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                }
                if(response.isSuccessful()){

                    try {
                        progressDialog.dismiss();

                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
                        dlgAlert.setMessage("تم ارسال طلب الشراء بنجاح و سوف يتم التواصل معك قريبا");
                        dlgAlert.setTitle("Joud");
                        dlgAlert.setIcon(R.drawable.logo);
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                      if(method.equals("bank_transfer")){
                          Intent i=new Intent(getContext(),attach_file.class);
                          i.putExtra("id",id);
                          startActivity(i);
                      }

                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),"هناك خطأ فى البيانات حاول المحاولة لاحقا ",Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"هناك خطأ فى البيانات حاول المحاولة لاحقا ",Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
            }
        });
    }
    public void payment(){
        Intent in = new Intent(getContext(), PayTabActivity.class);
        in.putExtra(PaymentParams.MERCHANT_EMAIL, "admin@atlatalrowad.com"); //this a demo account for testing the sdk
        in.putExtra(PaymentParams.SECRET_KEY,"jtinHAWuiY8oTrhhY1wOr2PSeRAZKT04mis3SoybmGzg7Qy6S4Vj7LclCMknGls2DU6viEGFkgVs7tyZxujFfv0SilgYGI0jC6PV");//Add your Secret Key Here
        in.putExtra(PaymentParams.LANGUAGE,PaymentParams.ENGLISH);
        in.putExtra(PaymentParams.TRANSACTION_TITLE, "Test Paytabs android library");
        in.putExtra(PaymentParams.AMOUNT,price);

        in.putExtra(PaymentParams.CURRENCY_CODE, "SAR");
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733");
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com");
        in.putExtra(PaymentParams.ORDER_ID, "123456");
        in.putExtra(PaymentParams.PRODUCT_NAME, getArguments().getString("name"));

//Billing Address
        in.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_BILLING, "Manama");
        in.putExtra(PaymentParams.STATE_BILLING, "Manama");
        in.putExtra(PaymentParams.COUNTRY_BILLING, "BHR");
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "00973"); //Put Country Phone code if Postal code not available '00973'

//Shipping Address
        in.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_SHIPPING, "Manama");
        in.putExtra(PaymentParams.STATE_SHIPPING, "Manama");
        in.putExtra(PaymentParams.COUNTRY_SHIPPING, "BHR");
        in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "00973"); //Put Country Phone code if Postal code not available '00973'

//Payment Page Style
        in.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc");

//Tokenization
        in.putExtra(PaymentParams.IS_TOKENIZATION, true);
        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            fetchInfo(true,"");
            Log.e("Tag", data.getStringExtra(PaymentParams.RESPONSE_CODE));
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID));
            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD));
            }
        }
    }
}
