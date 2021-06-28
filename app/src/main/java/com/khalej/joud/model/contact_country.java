package com.khalej.joud.model;

import com.google.gson.annotations.SerializedName;

public class contact_country {
    @SerializedName("id")
    int id;
    @SerializedName("ar_title")
    String ar_title;
    @SerializedName("en_title")
    String en_title;

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAr_title() {
        return ar_title;
    }

    public void setAr_title(String ar_title) {
        this.ar_title = ar_title;
    }
}
