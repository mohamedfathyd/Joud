package com.khalej.joud.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.joud.R;
import com.khalej.joud.model.Apiclient_home;
import com.khalej.joud.model.apiinterface_home;
import com.khalej.joud.model.contact_general_;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Terms_fragment extends Fragment {

    ImageView notification;
    TextView logout,terms,whous,callus,language,login,bank;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private contact_general_ contact;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_terms, container, false);
        notification=view.findViewById(R.id.notification);
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


        whous=view.findViewById(R.id.textt);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

        fetchInfo_annonce();
        return view;
    }

    public void fetchInfo_annonce() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_> call = apiinterface.getcontacts_g(sharedpref.getString("language","").trim());
        call.enqueue(new Callback<contact_general_>() {
            @Override
            public void onResponse(Call<contact_general_> call, Response<contact_general_> response) {
                contact=response.body();
                 whous.setText(android.text.Html.fromHtml(contact.getPayload().getAbout().getTerms_and_conditions().toString()));
            }

            @Override
            public void onFailure(Call<contact_general_> call, Throwable t) {

            }
        });
    }
}
