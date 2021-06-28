package com.khalej.joud.model;
import com.google.gson.annotations.SerializedName;


public class contact_userinfo {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("phone")
    String phone;
    @SerializedName("email")
    String maddress;
    @SerializedName("password")
    String Password;
    @SerializedName("national_image")
    String image;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("country_id")
    int country_id;
    @SerializedName("type")
    int usertype;
    @SerializedName("user")
    user user;

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public com.khalej.joud.model.user getUser() {
        return user;
    }

    public void setUser(com.khalej.joud.model.user user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMaddress() {
        return maddress;
    }

    public void setMaddress(String maddress) {
        this.maddress = maddress;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }
}
