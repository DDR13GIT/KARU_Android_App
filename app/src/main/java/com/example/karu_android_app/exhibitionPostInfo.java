package com.example.karu_android_app;

public class exhibitionPostInfo {

    String eventName;
    String eventPlace;
    String eventHost;
    String eventDate;
    double hostNID;
    double tktPrice;
    double bkashPayment;
    public exhibitionPostInfo() {
    }

    public exhibitionPostInfo(String eventName, String eventPlace, String eventHost, String eventDate, double hostNID, double tktPrice, double bkashPayment) {
        this.eventName = eventName;
        this.eventPlace = eventPlace;
        this.eventHost = eventHost;
        this.eventDate = eventDate;
        this.hostNID = hostNID;
        this.tktPrice = tktPrice;
        this.bkashPayment = bkashPayment;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getEventHost() {
        return eventHost;
    }

    public void setEventHost(String eventHost) {
        this.eventHost = eventHost;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public double getHostNID() {
        return hostNID;
    }

    public void setHostNID(double hostNID) {
        this.hostNID = hostNID;
    }

    public double getTktPrice() {
        return tktPrice;
    }

    public void setTktPrice(double tktPrice) {
        this.tktPrice = tktPrice;
    }

    public double getBkashPayment() {
        return bkashPayment;
    }

    public void setBkashPayment(double bkashPayment) {
        this.bkashPayment = bkashPayment;
    }
}
