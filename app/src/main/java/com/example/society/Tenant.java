package com.example.society;

public class Tenant {
    String name, flatNo, contact;

    public Tenant() {
    }

    public Tenant(String name, String flatNo, String contact) {
        this.name = name;
        this.flatNo = flatNo;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
