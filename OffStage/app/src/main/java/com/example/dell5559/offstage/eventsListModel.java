package com.example.dell5559.offstage;

public class eventsListModel {

    public String getEvent_id() {
        return event_id;
    }

    String event_id,eventName;

    public String getEventName() {
        return eventName;
    }

    public eventsListModel(String event_id,String eventName) {
        this.event_id = event_id;
        this.eventName = eventName;
    }
}
