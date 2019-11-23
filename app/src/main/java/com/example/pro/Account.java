package com.example.pro;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Account implements Serializable {
    private String name;
    private String email;
    private String userName;
    private String password;
    private String lastName;

    public Account(){

    }

    public Account(String name, String lastName, String userName, String password, String email){
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getPassword(){
        return this.password;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setUserName(String userName){this.userName = userName; }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name + " " + lastName + " Email: " + email + " Username: " + userName;
    }
}
