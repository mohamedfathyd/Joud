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
import com.khalej.joud.Activity.Sub_category_fragment;
import com.khalej.joud.R;
import com.khalej.joud.model.contact_category;
import com.khalej.joud.model.contact_general_Mycards;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_first_cardList extends RecyclerView.Adapter<RecyclerAdapter_first_cardList.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    private List<contact_general_Mycards.card> contactslist;
    RecyclerView recyclerView;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;

    public RecyclerAdapter_first_cardList(Context context, List<contact_general_Mycards.card> contactslist){
        this.contactslist=contactslist;
        this.context=context;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardplist,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
            edt = sharedpref.edit();
            if(sharedpref.getString("language","").trim().equals("ar")){
                holder.name.setText(contactslist.get(position).getCards().getName_by_lang());

            }else{
                holder.name.setText(contactslist.get(position).getCards().getName_by_lang());

            }
            holder.active.setText(contactslist.get(position).getStatus());
            holder.details.setText(contactslist.get(position).getCards().getOverview_by_lang());
            Glide.with(context).load(""+contactslist.get(position).getCards().getMedia_links()[0]).thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.card).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name,details,active;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.card_view);
            name=itemView.findViewById(R.id.Cardname);
            details=itemView.findViewById(R.id.carddetails);
            active=itemView.findViewById(R.id.active);
        }
    }}