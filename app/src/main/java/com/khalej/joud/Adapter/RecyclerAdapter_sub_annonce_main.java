package com.khalej.joud.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khalej.joud.Activity.Categry_details_fragment;
import com.khalej.joud.Activity.Sub_category_fragment;
import com.khalej.joud.R;
import com.khalej.joud.model.contact_category;
import com.khalej.joud.model.contact_general_sub_category;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_sub_annonce_main extends RecyclerView.Adapter<RecyclerAdapter_sub_annonce_main.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_category> contactslist;
    RecyclerView recyclerView;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Sub_category_fragment sub_category_fragment;
    public RecyclerAdapter_sub_annonce_main(Context context,  List<contact_category> contactslist,
                                            Sub_category_fragment sub_category_fragment){
        this.contactslist=contactslist;
        this.context=context;
        this.sub_category_fragment=sub_category_fragment;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_listt,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
            edt = sharedpref.edit();
            if(sharedpref.getString("language","").trim().equals("ar")){
                holder.name.setText(contactslist.get(position).getAr_name());

            }else{
                holder.name.setText(contactslist.get(position).getEn_name());

            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sub_category_fragment.fetchInfo_catrgory(contactslist.get(position).getId());
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);

        }
    }}