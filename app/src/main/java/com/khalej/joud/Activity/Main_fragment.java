package com.khalej.joud.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.JsonObject;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_category;
import com.khalej.joud.model.contact_general;
import com.khalej.joud.model.contact_general_category;
import com.khalej.joud.model.contact_slider;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_fragment extends Fragment {
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_first_annonce_banner recyclerAdapter_annonce;
    private RecyclerAdapter_first_annonce recyclerAdapter_first_annonce;
    TextView novalue;
    private contact_general_category contactList;

    private apiinterface_home apiinterface;
    private contact_slider contactList_annonce ;
    private contact_general contact_general;
    private  List<contact_category> contact_category;
    int x = 0;
    int y = 0;
    Switch swtch;
    int id;
    LottieAnimationView progressBar;
    ImageView notification;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main_fragment, container, false);
       // id = getArguments().getInt("id");
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        recyclerView3=view.findViewById(R.id.recyclerview3);
        notification=view.findViewById(R.id.notification);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        progressBar=view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
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

        fetchInfo_annonce();

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
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general> call = apiinterface.getcontacts_generalData();
        call.enqueue(new Callback<contact_general>() {
            @Override
            public void onResponse(Call<contact_general> call, Response<contact_general> response) {
           contact_general=response.body();
                   try {
                       contactList_annonce=contact_general.getPayload().getMedia();

                       List<String>Images= contactList_annonce.getSlider_images();
                        recyclerAdapter_annonce = new RecyclerAdapter_first_annonce_banner(getActivity(), Images, recyclerView2);
                        recyclerView2.setAdapter(recyclerAdapter_annonce);
                        fetchInfo_catrgory();


                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<contact_general> call, Throwable t) {

            }
        });
    }
    public void fetchInfo_catrgory() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_category> call = apiinterface.getcontacts_categores();
        call.enqueue(new Callback<contact_general_category>() {
            @Override
            public void onResponse(Call<contact_general_category> call, Response<contact_general_category> response) {
                progressBar.setVisibility(View.GONE);
                contactList = response.body();

               // Toast.makeText(getContext(),contact_category+"",Toast.LENGTH_LONG).show();
                try {
                    contact_category=contactList.getPayload();
                    if (contact_category.size() != 0 || !(contact_category.isEmpty())) {
                       recyclerAdapter_first_annonce= new RecyclerAdapter_first_annonce(getActivity(), contact_category);
                        final CarouselLayoutManager layoutManager2 = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
                        layoutManager2.setPostLayoutListener(new CarouselZoomPostLayoutListener());
                        recyclerView3.setLayoutManager(layoutManager2);
                        recyclerView3.setHasFixedSize(true);
                        recyclerView3.addOnScrollListener(new CenterScrollListener());
                        recyclerView3.setAdapter(recyclerAdapter_first_annonce);
                       }

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<contact_general_category> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
