package org.example.serversidesocialnetworkemo.Response;

public class UserHeaderResponse {

    private int id;
    private String profileUrl;
    private String username;



    public UserHeaderResponse(){}


    public UserHeaderResponse(int id, String profileUrl, String username) {
        this.id = id;
        this.profileUrl = profileUrl;
        this.username = username;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
