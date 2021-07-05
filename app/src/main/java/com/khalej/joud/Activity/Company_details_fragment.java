package com.khalej.joud.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.joud.Adapter.RecyclerAdapter_first_annonce_;
import com.khalej.joud.R;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_category;
import com.khalej.joud.model.contact_general;
import com.khalej.joud.model.contact_general_category;
import com.khalej.joud.model.contact_slider;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Company_details_fragment extends Fragment {
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_first_annonce_ recyclerAdapter_annonce;
    private RecyclerAdapter_first_annonce recyclerAdapter_first_annonce;
    TextView novalue;
    ArrayList<String> list2;
    private contact_general_category contactList;

    private apiinterface_home apiinterface;
    private contact_slider contactList_annonce ;
    private contact_general contact_general;
    private  List<contact_category> contact_category;
    int x = 0;
    int y = 0;
    Switch swtch;
    String id;
    ProgressBar progressBar;
    ImageView notification;
    TextView toolbar,name,carddetails,phone,address;
    String [] media;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_company_details, container, false);
        id = getArguments().getString("id");
        media=getArguments().getStringArray("image");
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        notification=view.findViewById(R.id.notification);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);
        toolbar=view.findViewById(R.id.toolbar_title);
        toolbar.setText(getArguments().getString("name"));
        carddetails=view.findViewById(R.id.carddetails);
        carddetails.setText(android.text.Html.fromHtml( getArguments().getString("details")).toString());
        phone=view.findViewById(R.id.phone);
        phone.setText( getArguments().getString("phone"));
        address=view.findViewById(R.id.address);
        address.setText(getArguments().getString("address"));
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+getArguments().getString("phone")));
                startActivity(intent);
            }
        });
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
       // Toast.makeText(getContext(),media[0]+"",Toast.LENGTH_LONG).show();
        list2 = new ArrayList<String>();
        for(String text:media) {
            list2.add(text);
        }
                try {
                   recyclerAdapter_annonce = new RecyclerAdapter_first_annonce_(getActivity(), list2);
                        recyclerView2.setAdapter(recyclerAdapter_annonce);


                } catch (Exception e) {
                }

    }

}
