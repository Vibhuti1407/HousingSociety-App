package com.example.society;

public class Member {
    String mflat, mfname, mcontact, memail, mpass;

    public Member() {
    }

    public Member(String mflat, String mfname, String mcontact) {
        this.mflat = mflat;
        this.mfname = mfname;
        this.mcontact = mcontact;
    }

    public Member(String mflat, String mfname, String mcontact, String memail, String mpass) {
        this.mflat = mflat;
        this.mfname = mfname;
        this.mcontact = mcontact;
        this.memail = memail;
        this.mpass = mpass;
    }

    public String getMflat() {
        return mflat;
    }

    public void setMflat(String mflat) {
        this.mflat = mflat;
    }

    public String getMfname() {
        return mfname;
    }

    public void setMfname(String mfname) {
        this.mfname = mfname;
    }

    public String getMcontact() {
        return mcontact;
    }

    public void setMcontact(String mcontact) {
        this.mcontact = mcontact;
    }

    public String getMemail() {
        return memail;
    }

    public void setMemail(String memail) {
        this.memail = memail;
    }

    public String getMpass() {
        return mpass;
    }

    public void setMpass(String mpass) {
        this.mpass = mpass;
    }
}
