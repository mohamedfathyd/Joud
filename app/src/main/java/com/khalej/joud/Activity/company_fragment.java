package com.khalej.joud.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.khalej.joud.Adapter.RecyclerAdapter_companys;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.joud.Adapter.RecyclerAdapter_sub_annonce;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_general_company;
import com.khalej.joud.model.contact_general_company;
import com.khalej.joud.model.contact_slider;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class company_fragment extends Fragment {
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_first_annonce_banner recyclerAdapter_annonce;
    private RecyclerAdapter_companys recyclerAdapter_first_annonce;
    TextView novalue;
    private contact_general_company contactList;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private apiinterface_home apiinterface;
    private contact_slider contactList_annonce ;
    private contact_general_company contact_general;
    private  List<contact_general_company.contact_company> contact_category;
    int x = 0;
    int y = 0;
    Switch swtch;
    String id;
    TextView toolbar_title;
    ProgressBar progressBar;
    ImageView notification;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_company, container, false);
        recyclerView3=view.findViewById(R.id.recyclerview3);
        notification=view.findViewById(R.id.notification);
        layoutManager = new GridLayoutManager(getContext(), 2);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
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
       StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        3, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(staggeredGridLayoutManager);
        recyclerView3.setHasFixedSize(true);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        fetchInfo_catrgory();


        return view;
    }

    public void fetchInfo_catrgory() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_company> call = apiinterface.getcontacts_companies(sharedpref.getString("language","").trim());
        call.enqueue(new Callback<contact_general_company>() {
            @Override
            public void onResponse(Call<contact_general_company> call, Response<contact_general_company> response) {
                progressBar.setVisibility(View.GONE);
                contactList = response.body();

               // Toast.makeText(getContext(),contact_category+"",Toast.LENGTH_LONG).show();
                try {
                    contact_category=contactList.getPayload();
                    if (contact_category.size() != 0 || !(contact_category.isEmpty())) {
                       recyclerAdapter_first_annonce= new RecyclerAdapter_companys(getActivity(), contact_category);
                        recyclerView3.setAdapter(recyclerAdapter_first_annonce);
                       }

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<contact_general_company> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}