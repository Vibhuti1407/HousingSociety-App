package com.example.society;

public class Admin {
    String aflat, afname, acontact, aemail, apass;

    public Admin(String aflat, String afname, String acontact, String aemail, String apass) {
        this.aflat = aflat;
        this.afname = afname;
        this.acontact = acontact;
        this.aemail = aemail;
        this.apass = apass;
    }

    public String getAflat() {
        return aflat;
    }

    public void setAflat(String aflat) {
        this.aflat = aflat;
    }

    public String getAfname() {
        return afname;
    }

    public void setAfname(String afname) {
        this.afname = afname;
    }

    public String getAcontact() {
        return acontact;
    }

    public void setAcontact(String acontact) {
        this.acontact = acontact;
    }

    public String getAemail() {
        return aemail;
    }

    public void setAemail(String aemail) {
        this.aemail = aemail;
    }

    public String getApass() {
        return apass;
    }

    public void setApass(String apass) {
        this.apass = apass;
    }
}
