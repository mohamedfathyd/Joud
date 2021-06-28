package com.khalej.joud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_general_Mycards {
    @SerializedName("payload")
   List <card> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<card> getPayload() {
        return payload;
    }

    public void setPayload(List<card> payload) {
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
public class card{
        @SerializedName("cards")
     contact_cards cards;
        @SerializedName("status")
        String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public contact_cards getCards() {
        return cards;
    }

    public void setCards(contact_cards cards) {
        this.cards = cards;
    }
}
    public class  contact_cards{
        @SerializedName("media_links")
        String[]media_links;
        @SerializedName("name_by_lang")
        String name_by_lang;
        @SerializedName("overview_by_lang")
        String overview_by_lang;

        public String[] getMedia_links() {
            return media_links;
        }

        public void setMedia_links(String[] media_links) {
            this.media_links = media_links;
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
    }
}
