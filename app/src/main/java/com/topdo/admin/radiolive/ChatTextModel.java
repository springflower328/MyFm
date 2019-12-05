package com.topdo.admin.radiolive;

public class ChatTextModel {

    public String chatmsg;
    public String chattime;
    public String icon;

    public ChatTextModel(String chatmsg, String chattime, String icon) {
        this.chatmsg = chatmsg;
        this.chattime = chattime;
        this.icon = icon;
    }

    public ChatTextModel() {
    }


    public String getchatmsg() {
        return chatmsg;
    }

    public void setchatmsg(String chatmsg) {
        this.chatmsg = chatmsg;
    }

    public String getchattime() {
        return chattime;
    }

    public void setchattime(String chattime) {
        this.chattime = chattime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
