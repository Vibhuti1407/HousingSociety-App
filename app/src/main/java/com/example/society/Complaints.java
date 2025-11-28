package com.example.society;

public class Complaints {
    String date,flatNo,description;

    public Complaints() {
    }

    public Complaints(String date, String flatNo, String description) {
        this.date = date;
        this.flatNo = flatNo;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
