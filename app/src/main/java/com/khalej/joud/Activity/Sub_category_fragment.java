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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.joud.Adapter.RecyclerAdapter_sub_annonce;
import com.khalej.joud.Adapter.RecyclerAdapter_sub_annonce_main;
import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_category;
import com.khalej.joud.model.contact_general;
import com.khalej.joud.model.contact_general_category;
import com.khalej.joud.model.contact_general_sub_category;
import com.khalej.joud.model.contact_slider;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;

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

public class Sub_category_fragment extends Fragment {
    private RecyclerView recyclerView, recyclerView2, recyclerView3,recyclerView4;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_first_annonce_banner recyclerAdapter_annonce;
    private RecyclerAdapter_sub_annonce recyclerAdapter_first_annonce;

    TextView novalue;
    private contact_general_sub_category contactList;

    private apiinterface_home apiinterface;
    private contact_slider contactList_annonce ;
    private contact_general_sub_category contact_general;
    private  List<contact_general_sub_category.contact_sub_category> contact_category;
    private RecyclerAdapter_sub_annonce_main recyclerAdapter_annonce_;
    int x = 0;
    private SharedPreferences sharedpref;
    int y = 0;
    Switch swtch;
    String id;
    TextView toolbar_title;
    LottieAnimationView progressBar;
    ImageView notification;
    private contact_general contact_general__;
    private  List<contact_category> contact_category_;
    private contact_general_category contactList_;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        id = getArguments().getString("id","");
        toolbar_title=view.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getArguments().getString("name","Joud"));
        recyclerView3=view.findViewById(R.id.recyclerview3);
        recyclerView4=view.findViewById(R.id.recyclerview4);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        notification=view.findViewById(R.id.notification);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
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
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView3.setHasFixedSize(true);
        fetchInfo_catrgory_main();

        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);
        fetchInfo_catrgory_main();
        fetchInfo_annonce();
        return view;
    }

    public void fetchInfo_catrgory(String id) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView4.setAdapter(null);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_sub_category> call = apiinterface.getSubCategory(id,sharedpref.getString("language","").trim());
        call.enqueue(new Callback<contact_general_sub_category>() {
            @Override
            public void onResponse(Call<contact_general_sub_category> call, Response<contact_general_sub_category> response) {
                progressBar.setVisibility(View.GONE);
                contactList = response.body();

               // Toast.makeText(getContext(),contact_category+"",Toast.LENGTH_LONG).show();
                try {
                    contact_category=contactList.getPayload();
                    if (contact_category.size() != 0 || !(contact_category.isEmpty())) {
                       recyclerAdapter_first_annonce= new RecyclerAdapter_sub_annonce(getActivity(), contact_category);
                        final CarouselLayoutManager layoutManager2 = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
                        layoutManager2.setPostLayoutListener(new CarouselZoomPostLayoutListener((float) 0.5));
                        recyclerView4.setLayoutManager(layoutManager2);
                        recyclerView4.setHasFixedSize(true);
                        recyclerView4.addOnScrollListener(new CenterScrollListener());
                        recyclerView4.setAdapter(recyclerAdapter_first_annonce);
                       }

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<contact_general_sub_category> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });

    }
    public void fetchInfo_annonce() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general> call = apiinterface.getcontacts_generalData();
        call.enqueue(new Callback<contact_general>() {
            @Override
            public void onResponse(Call<contact_general> call, Response<contact_general> response) {
                contact_general__=response.body();
                try {
                    contactList_annonce=contact_general__.getPayload().getMedia();

                    List<String>Images= contactList_annonce.getSlider_images();
                    recyclerAdapter_annonce = new RecyclerAdapter_first_annonce_banner(getActivity(), Images, recyclerView2);
                    recyclerView2.setAdapter(recyclerAdapter_annonce);
                    fetchInfo_catrgory(id);

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<contact_general> call, Throwable t) {

            }
        });
    }
    public void fetchInfo_catrgory_main() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_category> call = apiinterface.getcontacts_categores();
        call.enqueue(new Callback<contact_general_category>() {
            @Override
            public void onResponse(Call<contact_general_category> call, Response<contact_general_category> response) {
               // progressBar.setVisibility(View.GONE);
                contactList_ = response.body();

                // Toast.makeText(getContext(),contact_category+"",Toast.LENGTH_LONG).show();
                try {
                    contact_category_=contactList_.getPayload();
                    if (contact_category_.size() != 0 || !(contact_category_.isEmpty())) {
                        recyclerAdapter_annonce_= new RecyclerAdapter_sub_annonce_main(getActivity(), contact_category_,Sub_category_fragment.this);

                        recyclerView3.setAdapter(recyclerAdapter_annonce_);
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
