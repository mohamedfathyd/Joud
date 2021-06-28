package com.khalej.joud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_general_sub_category {
    @SerializedName("payload")
    List<contact_sub_category> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<contact_sub_category> getPayload() {
        return payload;
    }

    public void setPayload(List<contact_sub_category> payload) {
        this.payload = payload;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public class contact_sub_category {
        @SerializedName("id")
        String id;
        @SerializedName("price")
        int price;
        @SerializedName("discount")
        String discount;
        @SerializedName("frequency")
        String frequency;
        @SerializedName("name_by_lang")
        String name_by_lang;
        @SerializedName("overview_by_lang")
        String overview_by_lang;
        @SerializedName("media_links")
        String[] media_links;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getName_by_lang() {
            return name_by_lang;
        }

        public void setName_by_lang(String name_by_lang) {
            this.name_by_lang = name_by_lang;
        }

        public String getOverview_by_lang() {
            return overview_by_lang;
        }

        public void setOverview_by_lang(String overview_by_lang) {
            this.overview_by_lang = overview_by_lang;
        }

        public String[] getMedia_links() {
            return media_links;
        }


    }
}
