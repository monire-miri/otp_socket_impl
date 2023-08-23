package org.example.model;

public class VerifyOtpRequest {
    private String phonenumber;
    private String email;
    private int otp;

    public String getPhonenumber() {
        return phonenumber;
    }
    @Override
    public String toString() {
        return "VerifyOtpRequest{" +
                "phonenumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                ", otp=" + otp +
                '}';
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }




}
