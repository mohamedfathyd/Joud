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
import com.khalej.joud.Activity.Company_details_fragment;
import com.khalej.joud.Activity.Sub_category_fragment;
import com.khalej.joud.R;
import com.khalej.joud.model.contact_general_company;
import com.khalej.joud.model.contact_general_sub_category;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_companys extends RecyclerView.Adapter<RecyclerAdapter_companys.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_general_company.contact_company> contactslist;
    RecyclerView recyclerView;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;

    public RecyclerAdapter_companys(Context context, List<contact_general_company.contact_company> contactslist){
        this.contactslist=contactslist;
        this.context=context;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("language","").trim().equals("ar")){
            view=  LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_ar,parent,false); }
        else{
            view=  LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list,parent,false);

        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
            edt = sharedpref.edit();
            if(sharedpref.getString("language","").trim().equals("ar")){
                holder.name.setText(contactslist.get(position).getAr_name());
              if(position%2!=0){
                  holder.rec.setBackgroundResource(R.drawable.rectangleorange);
              }
            }else{
                holder.name.setText(contactslist.get(position).getEn_name());
                if(position%2!=0){
                    holder.rec.setBackgroundResource(R.drawable.rectangleorangeen);
                }
            }
            try {
                Glide.with(context).load("https://joudcard.com"+contactslist.get(position).getMedia_links()[0]).thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.card).into(holder.image);
            }catch (Exception e){}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Company_details_fragment subCategoryFrag= new Company_details_fragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle bundle = new Bundle();
                bundle.putString("id",contactslist.get(position).getId());
                bundle.putStringArray("image",contactslist.get(position).getMedia_links());
                bundle.putString("details",contactslist.get(position).getDetails());
                bundle.putString("phone",contactslist.get(position).getPhone());
               if(sharedpref.getString("language","").trim().equals("ar")){
                    bundle.putString("name",contactslist.get(position).getAr_name());
                   bundle.putString("address",contactslist.get(position).getAr_address());
                }else{
                    bundle.putString("name",contactslist.get(position).getEn_name());
                   bundle.putString("address",contactslist.get(position).getAddress());
                }
                subCategoryFrag.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, subCategoryFrag)
                        .addToBackStack( "tag" ).commit();;
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image,rec;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.image);
            rec=itemView.findViewById(R.id.rec);
            name=itemView.findViewById(R.id.name);

        }
    }}