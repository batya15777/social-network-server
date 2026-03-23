package org.example.serversidesocialnetworkemo.Entity;

public class User {
//private int id;
 private String name;
 private String lastName;
 private String phone;
 private String generalSex;
 private String username;
 private String password;
 private String token;
 private String profileUrl;





 public User(){


 }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getGeneralSex() {
        return generalSex;
    }

    public void setGeneralSex(String generalSex) {
        this.generalSex = generalSex;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
