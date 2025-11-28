package com.example.society;

public class Payment {
    String status, flat;

    public Payment() {
    }

    public Payment(String flat){
        this.flat = flat;
    }
    public Payment(String status, String flat) {
        this.status = status;
        this.flat = flat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

}
