package com.khalej.joud.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khalej.joud.Adapter.RecyclerAdapter_companys;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.joud.Adapter.RecyclerAdapter_first_cardList;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_category;
import com.khalej.joud.model.contact_general_Mycards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile_fragment extends Fragment {

    ImageView notification;
    TextView logout,terms,whous,callus,language,login,bank;
    Button edit;
    CircleImageView image;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private RecyclerView.LayoutManager layoutManager;
    TextView name ,address,phone,Cardname,carddetails;
    ImageView card_view;
    RecyclerAdapter_first_cardList recyclerAdapter_annonce;
    private apiinterface_home apiinterface;
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private contact_general_Mycards contactList;
    private List<contact_general_Mycards.card> contact_cards =new ArrayList<>() ;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        notification=view.findViewById(R.id.notification);
        edit=view.findViewById(R.id.cunti);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Notification_fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack("tag");
                fragmentTransaction.commit();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new edit_fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack("tag");
                fragmentTransaction.commit();
            }
        });

     //   whous=view.findViewById(R.id.text);
//        Cardname=view.findViewById(R.id.Cardname);
//        carddetails=view.findViewById(R.id.carddetails);
        image=view.findViewById(R.id.image);
        name=view.findViewById(R.id.username);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);
//        card_view=view.findViewById(R.id.card_view);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();


        Glide.with(getContext()).load(sharedpref.getString("image","")).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.logo).into(image);

        name.setText(sharedpref.getString("name",""));
        address.setText(sharedpref.getString("address",""));
        phone.setText(sharedpref.getString("phone",""));
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
        if(sharedpref.getString("remember","").equals("yes")){
            fetchInfo_card();
            // payment();
        }
        else{
          //  Toast.makeText(getContext(),"قم بتسجيل الدخول أولا" ,Toast.LENGTH_LONG).show();
        }


        return view;
    }
    public void fetchInfo_card() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_Mycards> call = apiinterface.getcards(headers,sharedpref.getString("id",""),
                sharedpref.getString("language","").trim());
        call.enqueue(new Callback<contact_general_Mycards>() {
            @Override
            public void onResponse(Call<contact_general_Mycards> call, Response<contact_general_Mycards> response) {
                contactList = response.body();

                // Toast.makeText(getContext(),contact_category+"",Toast.LENGTH_LONG).show();
                try {
                    contact_cards=contactList.getPayload();
                    recyclerAdapter_annonce = new RecyclerAdapter_first_cardList(getActivity(), contact_cards);
                    recyclerView2.setAdapter(recyclerAdapter_annonce);


                } catch (Exception e) {
                 
                }
            }

            @Override
            public void onFailure(Call<contact_general_Mycards> call, Throwable t) {
             
            }
        });
    }

}
