package com.nikitos.homeflow.Model;

public class Form {
    private int id;
    private String address;
    private String fullName;
    private String phoneNumber;
    private String who_is_needed;
    private boolean isProcessed;


    public Form(int id, String address, String fullName, String phoneNumber, String who_is_needed, boolean isProcessed) {
        this.id = id;
        this.address = address;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.who_is_needed = who_is_needed;
        this.isProcessed = isProcessed;
    }


    public Form(int id, String address, String fullName, String phoneNumber, String who_is_needed) {
        this.id = id;
        this.address = address;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.who_is_needed = who_is_needed;
    }


    public Form(String address, String fullName, String phoneNumber, String who_is_needed, boolean isProcessed) {
        this.address = address;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.who_is_needed = who_is_needed;
        this.isProcessed = isProcessed;
    }


    public Form(String address, String fullName, String phoneNumber, String who_is_needed) {
        this.address = address;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.who_is_needed = who_is_needed;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWhoIsNeeded() {
        return who_is_needed;
    }
    public void setWhoIsNeeded(String who_is_needed) {
        this.who_is_needed = who_is_needed;
    }

    public boolean isProcessed() {
        return isProcessed;
    }
    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
}
