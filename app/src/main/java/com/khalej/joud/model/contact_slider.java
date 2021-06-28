package com.khalej.joud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_slider {
    @SerializedName("logo")
    String logo;
    @SerializedName("slider_images")
    List<String> slider_images;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<String> getSlider_images() {
        return slider_images;
    }

    public void setSlider_images(List<String> slider_images) {
        this.slider_images = slider_images;
    }
}
