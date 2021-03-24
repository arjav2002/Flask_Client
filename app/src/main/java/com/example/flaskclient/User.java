package com.example.flaskclient;

public class User {

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
