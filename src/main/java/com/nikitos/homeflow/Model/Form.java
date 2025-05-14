package com.nikitos.homeflow.Model;

import java.time.LocalDateTime;

public class Form {
    private int id;
    private String address;
    private String fullName;
    private String phoneNumber;
    private String who_is_needed;
    private LocalDateTime dateCreated;
    private boolean isProcessing;
    private boolean isFinished;


    public Form(int id, String address, String fullName, String phoneNumber, String who_is_needed, LocalDateTime dateCreated, boolean isProcessing, boolean isFinished) {
        this.id = id;
        this.address = address;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.who_is_needed = who_is_needed;
        this.isProcessing = isProcessing;
        this.dateCreated = dateCreated;
        this.isFinished = isFinished;
    }


    public Form(String address, String fullName, String phoneNumber, String who_is_needed) {
        this.address = address;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.who_is_needed = who_is_needed;
        this.dateCreated = LocalDateTime.now();
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isProcessing() {
        return isProcessing;
    }
    public void setProcessed(boolean processed) {
        isProcessing = processed;
    }

    public boolean isFinished() {
        return isFinished;
    }
    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
