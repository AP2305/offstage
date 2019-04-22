package com.example.dell5559.offstage;

public class ParticipantModel {

    String pid;
    String pname;
    String pcontact;
    String alt_contact;
    String pemail;
    String isPresent;
    String event_name;
    String category;

    public ParticipantModel(String pid, String pname, String pcontact, String alt_contact, String pemail, String evname, String isPresent,String category) {
        this.pid = pid;
        this.pname = pname;
        this.pcontact = pcontact;
        this.pemail = pemail;
        this.event_name = evname;
        this.alt_contact = alt_contact;
        this.isPresent = isPresent;
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getPcontact() {
        return pcontact;
    }

    public String getAlt_contact() { return alt_contact; }

    public String getPemail() {
        return pemail;
    }

    public String getEvent_name() { return event_name;}

    public String getCategory() {
        return category;
    }

    public String getIsPresent() {
        return isPresent;
    }

}
