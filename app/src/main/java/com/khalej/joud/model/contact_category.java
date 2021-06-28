package com.khalej.joud.model;
import com.google.gson.annotations.SerializedName;


public class contact_category {
    @SerializedName("id")
    String id;
    @SerializedName("en_name")
    String en_name;
    @SerializedName("ar_name")
    String ar_name;
    @SerializedName("icon")
    String icon;
    @SerializedName("status")
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
