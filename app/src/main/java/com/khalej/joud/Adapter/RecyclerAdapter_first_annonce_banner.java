package com.khalej.joud.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khalej.joud.R;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_first_annonce_banner extends RecyclerView.Adapter<RecyclerAdapter_first_annonce_banner.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<String> contactslist;
    RecyclerView recyclerView;

    public RecyclerAdapter_first_annonce_banner(Context context, List<String> contactslist, RecyclerView recyclerView){
        this.contactslist=contactslist;
        this.context=context;
        this.recyclerView=recyclerView;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.annonce_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {


            Glide.with(context).load(""+contactslist.get(position)).thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.logo).into(holder.image);
        }
        catch (Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog settingsDialog = new Dialog(context);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(R.layout.image_show);
                ImageView img = (ImageView) settingsDialog.findViewById(R.id.img);
                Glide.with(context).load(""+contactslist.get(position)).error(R.drawable.logo).into(img);
                settingsDialog.show();
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.photo);

        }
    }}