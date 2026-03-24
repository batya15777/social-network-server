package org.example.serversidesocialnetworkemo.Response;

import java.util.Date;

public class CommentResponse {

    private String username;
    private String profileUrl;
    private String content;
    private Date created_at;



    public CommentResponse(){}
    public CommentResponse(String username, String profileUrl, String content, Date created_at) {
        this.username = username;
        this.profileUrl = profileUrl;
        this.content = content;
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
