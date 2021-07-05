package com.khalej.joud.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.joud.Adapter.RecyclerAdapter_notification;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_category;
import com.khalej.joud.model.contact_general;
import com.khalej.joud.model.contact_notification;
import com.khalej.joud.model.contact_slider;

import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_fragment extends Fragment {
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_first_annonce_banner recyclerAdapter_annonce;
    private RecyclerAdapter_notification recyclerAdapter_first_annonce;
    TextView novalue;
    private contact_notification contactList;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;

    private apiinterface_home apiinterface;
    private contact_slider contactList_annonce ;
    private contact_general contact_general;
    private  List<contact_notification.media> contact_category;
    int x = 0;
    int y = 0;
    Switch swtch;
    String id;
    LottieAnimationView progressBar;
    SearchView searchView;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView3=view.findViewById(R.id.recyclerview3);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        progressBar=view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager = new GridLayoutManager(getContext(), 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(staggeredGridLayoutManager);
        recyclerView3.setHasFixedSize(true);
        fetchInfo_notification();

        return view;
    }


    public void fetchInfo_notification() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        Call<contact_notification> call = apiinterface.getnotification(headers);
        call.enqueue(new Callback<contact_notification>() {
            @Override
            public void onResponse(Call<contact_notification> call, Response<contact_notification> response) {
                progressBar.setVisibility(View.GONE);
                contactList = response.body();
             //   contact_category=contactList.getPayload();
               // Toast.makeText(getContext(),contact_category+"",Toast.LENGTH_LONG).show();
                try {
                    contact_category=contactList.getPayload();

                    if (contact_category.size() != 0 || !(contact_category.isEmpty())) {
                       recyclerAdapter_first_annonce= new RecyclerAdapter_notification(getActivity(), contact_category);
                        recyclerView3.setAdapter(recyclerAdapter_first_annonce);
                       }

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<contact_notification> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
