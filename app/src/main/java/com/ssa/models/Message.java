package com.ssa.models;

import java.util.Date;

public class Message/* implements IMessage,MessageContentType.Image, *//*this is for default image messages implementation*//*
        MessageContentType*/ {

    String text, senderName, senderId, status;
    private Date createdAt;

//    @Override
//    public String getId() {
//        return senderId;
//    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /*@Override
    public User getUser() {
        return new User(
                "1",
                senderName,
                text,
                true);
    }*/

//    @Override
//    public Date getCreatedAt() {
//        return createdAt;
//    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    @Override
//    public String getImageUrl() {
//        return "";
//    }
}
