package com.khalej.joud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_general_company {
    @SerializedName("payload")
    List<contact_company> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<contact_company> getPayload() {
        return payload;
    }

    public void setPayload(List<contact_company> payload) {
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
    public class contact_company {
        @SerializedName("id")
        String id;
        @SerializedName("media_links")
        String[] media_links;
        @SerializedName("en_name")
        String en_name;
        @SerializedName("ar_name")
        String ar_name;
        @SerializedName("phone")
        String phone;
        @SerializedName("overview_by_lang")
        String details;
        @SerializedName("en_address")
        String address;
        @SerializedName("ar_address")
        String ar_address;

        public String getAr_address() {
            return ar_address;
        }

        public void setAr_address(String ar_address) {
            this.ar_address = ar_address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setMedia_links(String[] media_links) {
            this.media_links = media_links;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String[] getMedia_links() {
            return media_links;
        }


    }
}
