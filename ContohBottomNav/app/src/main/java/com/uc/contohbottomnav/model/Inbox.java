package com.uc.contohbottomnav.model;

import java.io.Serializable;

public class Inbox implements Serializable {

    private String id;
    private String title;
    private String content;
    private String senttime;

    public Inbox(){}

    public Inbox(String id, String title, String content, String senttime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.senttime = senttime;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSenttime() {
        return senttime;
    }
}
