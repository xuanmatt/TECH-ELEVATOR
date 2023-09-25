package com.lendingcatalog.model;

import java.util.UUID;

public class Member {
    private String firstName;
    private String lastName;

    public Member(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        //UUID.randomUUID().toString();

        return firstName + " " + lastName;
    }






}
