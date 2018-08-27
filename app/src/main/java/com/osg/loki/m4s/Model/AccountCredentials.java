package com.osg.loki.m4s.Model;

/**
 * Created by ergas on 5/28/2018.
 */

public class AccountCredentials {
    private String username;
    private String password;

    public AccountCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
