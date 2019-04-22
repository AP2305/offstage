package com.example.dell5559.offstage;

public class ExpenseModel {

    public ExpenseModel(String tid,String payeer, String datetime, String type, String mode, String amount, String desc, String image, String sid,String event_name,String isverified) {
        this.tid = tid;
        this.payeer = payeer;
        this.datetime = datetime;
        this.type = type;
        this.mode = mode;
        this.amount = amount;
        this.desc = desc;
        this.image = image;
        this.sid = sid;
        this.event_name = event_name;
        this.isverified = isverified;
    }

    String payeer;
    String datetime;
    String type;
    String isverified;
    String mode;
    String amount;
    String desc;
    String image;
    String sid;
    String event_name;
    String tid;

    public String getEvent_name() {
        return event_name;
    }
    public String getTid() {
        return tid;
    }
    public String getPayeer() {
        return payeer;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getType() {
        return type;
    }

    public String getMode() {
        return mode;
    }

    public String getAmount() {
        return amount;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getSid() {
        return sid;
    }
    public String getIsverified() {
        return isverified;
    }
}
