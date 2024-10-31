package com.example.contentprovider.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Contact implements Serializable {
    String name;
    String phone;
    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @NonNull
    @Override
    public String toString() {
        return "Tên: " +name+"\nPhone: " +this.phone;
    }
}
