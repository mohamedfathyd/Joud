package com.khalej.joud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_general_category {
    @SerializedName("payload")
    List<contact_category> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<contact_category> getPayload() {
        return payload;
    }

    public void setPayload(List<contact_category> payload) {
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

}
