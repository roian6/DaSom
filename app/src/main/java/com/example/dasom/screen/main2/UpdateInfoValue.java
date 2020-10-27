package com.example.dasom.screen.main2;

public class UpdateInfoValue {
    private String contact;
    private String username;

    public UpdateInfoValue() {

    }

    public UpdateInfoValue(String contact, String username) {
        this.contact = contact;
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
