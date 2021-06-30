package com.khalej.joud.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_category;
import com.khalej.joud.model.contact_general;
import com.khalej.joud.model.contact_general_;
import com.khalej.joud.model.contact_general_category;
import com.khalej.joud.model.contact_slider;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class More_fragment extends Fragment {

    ImageView notification;
    TextView logout,terms,whous,callus,language,login,bank,noti;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    CircleImageView image;
    TextView name ,address,phone ;
    LinearLayout logoutLinear;
    private contact_general_ contact;
    private  static final int IMAGEUser = 99;
    Bitmap bitmapUser;
    String mediaPath,mediaPathId;
    String imagePath;
    ProgressDialog progressDialog;
    LinearLayout logut;
    ImageView addorder;
    ImageView face,inst,twitter,linkedin,youtube;
    private apiinterface_home apiinterface;
    private static final int MY_CAMERA_PERMISSION_CODE = 1;
    private static final int CAMERA_REQUEST = 1;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_more, container, false);
        notification=view.findViewById(R.id.notification);
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

        logout=view.findViewById(R.id.logout);
        terms=view.findViewById(R.id.terms);
        whous=view.findViewById(R.id.whous);
        callus=view.findViewById(R.id.callus);
        language=view.findViewById(R.id.language);
        logut=view.findViewById(R.id.logut);
        image=view.findViewById(R.id.image);
        name=view.findViewById(R.id.username);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);
        bank=view.findViewById(R.id.bank);
        face=view.findViewById(R.id.face);
        inst=view.findViewById(R.id.inst);
        twitter=view.findViewById(R.id.twitter);
        linkedin=view.findViewById(R.id.linkedin);
        youtube=view.findViewById(R.id.youtube);
        logoutLinear=view.findViewById(R.id.logoutLinear);
        noti=view.findViewById(R.id.noti);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

        fetchInfo_annonce();
        Glide.with(getContext()).load(sharedpref.getString("image","")).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.logo).into(image);

        name.setText(sharedpref.getString("name",""));
//        if(sharedpref.getInt("type",0)==2||sharedpref.getInt("type",0)==1){
//            address.setText( "الرصيد :" +sharedpref.getFloat("wallet",0));
//            address.setVisibility(View.GONE);
//            addorder.setVisibility(View.VISIBLE);
//        }
//        else{
//            address.setVisibility(View.GONE);
//            addorder.setVisibility(View.GONE);
//        }
        address.setText(sharedpref.getString("address",""));
        phone.setText(sharedpref.getString("phone",""));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new profile_fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        noti.setOnClickListener(new View.OnClickListener() {
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
        logoutLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.putInt("id",0);
                edt.putString("name","");
                edt.putString("image","");
                edt.putString("phone","");
                edt.putString("address","");
                edt.putString("password","");
                edt.putString("createdAt","");
                edt.putInt("type",0);
                edt.putFloat("wallet",0);
                edt.putString("remember","no");
                edt.apply();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });


        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try{
//                    String url = "https://api.whatsapp.com/send?phone="+"+97333348098";
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    startActivity(i);}
//                catch( Exception e){
//                    Toast.makeText(getActivity(), "غير متاحه الأن عاود المحاولة لاحقا " ,Toast.LENGTH_LONG).show();
//                }

            startActivity(new Intent(getContext(),CallUs.class));
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog settingsDialog = new Dialog(getContext());
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(R.layout.image_show);
                ImageView img = (ImageView) settingsDialog.findViewById(R.id.img);
                Glide.with(getContext()).load(sharedpref.getString("image","")).error(R.drawable.logo).into(img);
                settingsDialog.show();
            }
        });
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Main_fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedpref.getString("language","").trim().equals("ar")){
                    edt.putString("language","en");
                    edt.apply();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                    getActivity().finish();
                }
                else
                {
                    edt.putString("language","ar");
                    edt.apply();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                    getActivity().finish();
                }
            }
        });

        whous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new whous_fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Terms_fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
face.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try{
        String url = contact.getPayload().getSocial().getFacebook();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);}catch (Exception e){}
    }
});
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                String url = contact.getPayload().getSocial().getInstagram();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);}catch (Exception e){}
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                String url = contact.getPayload().getSocial().getTwitter();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);}catch (Exception e){}
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                String url = contact.getPayload().getSocial().getLinkedin();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);}catch (Exception e){}
            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                String url = contact.getPayload().getSocial().getYoutube();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);}catch (Exception e){}
            }
        });
        return view;
    }

    public void fetchInfo_annonce() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_> call = apiinterface.getcontacts_g(sharedpref.getString("language","").trim());
        call.enqueue(new Callback<contact_general_>() {
            @Override
            public void onResponse(Call<contact_general_> call, Response<contact_general_> response) {
                contact=response.body();

            }

            @Override
            public void onFailure(Call<contact_general_> call, Throwable t) {

            }
        });
    }
}
