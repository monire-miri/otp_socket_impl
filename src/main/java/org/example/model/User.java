package org.example.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {
    private String phonenumber;
    private String email;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
       this.phonenumber=phonenumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "phoneNumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
