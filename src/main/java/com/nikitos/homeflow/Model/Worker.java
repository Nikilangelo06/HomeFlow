package com.nikitos.homeflow.Model;

public class Worker {
    private int id;
    private String fullName;
    private int profession;
    private String phoneNumber;
    private Boolean isAvailable;


    public Worker(int id, String fullName, int profession, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
    }

    public Worker(String fullName, int profession, String phoneNumber) {
        this.fullName = fullName;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
    }

    public Worker(int id, String fullName, int profession, String phoneNumber, Boolean isAvailable) {
        this.id = id;
        this.fullName = fullName;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.isAvailable = isAvailable;
    }

    public Worker(String fullName, int profession, String phoneNumber, Boolean isAvailable) {
        this.fullName = fullName;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.isAvailable = isAvailable;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Boolean IsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
