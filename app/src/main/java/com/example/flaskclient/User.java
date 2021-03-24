package com.example.flaskclient;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String name, address, phno, dad, mom, emcon, guardian, age;

    public User(String name, String address, String phno, String dad, String mom, String emcon, String guardian, String age) {
        this.name = name;
        this.address = address;
        this.phno = phno;
        this.dad = dad;
        this.mom = mom;
        this.emcon = emcon;
        this.guardian = guardian;
        this.age = age;
    }

    protected User(Parcel in) {
        name = in.readString();
        address = in.readString();
        phno = in.readString();
        dad = in.readString();
        mom = in.readString();
        emcon = in.readString();
        guardian = in.readString();
        age = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phno);
        dest.writeString(dad);
        dest.writeString(mom);
        dest.writeString(emcon);
        dest.writeString(guardian);
        dest.writeString(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhno() {
        return phno;
    }

    public String getDad() {
        return dad;
    }

    public String getMom() {
        return mom;
    }

    public String getEmcon() {
        return emcon;
    }

    public String getGuardian() {
        return guardian;
    }

    public String getAge() {
        return age;
    }
}
