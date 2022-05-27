package com.example.fozmusicstore.data.models;


import java.util.HashMap;
import java.util.Map;

public class User {
    private String userKey;
    private String fullName;
    private String username;
    private String password;

    public User(String userKey, String fullName, String username, String password){
        this.userKey = userKey;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public String getUserKey() {

        return userKey;
    }

    public String getFullName() {

        return fullName;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {

        return password;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> data = new HashMap<>();
        data.put("fullName", fullName);
        data.put("username", username);
        data.put("password", password);
        return data;
    }

}
