package org.example.serversidesocialnetworkemo.Request;

import java.util.Date;

public class PostRequest {

    private int id;
    private String username;
    private String profileUrl;
    private String content;
    private Date createAt;
    private String image_url;
    private int likes;
    private boolean userLiked;
    private int comments;


    public PostRequest(String username, String profileUrl, String content, int id, String image_url, Date createAt, int likes, int comments) {
        this.username = username;
        this.profileUrl = profileUrl;
        this.content = content;
        this.id = id;
        this.image_url = image_url;
        this.createAt = createAt;
        this.likes = likes;
        this.comments = comments;
    }

    public PostRequest(int id, String content, Date createAt, String image_url,
                       int likes, boolean userLiked, int comments) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.image_url = image_url;
        this.likes = likes;
        this.userLiked = userLiked;
        this.comments = comments;
    }

    public PostRequest(int id, String content, Date createAt, String image_url,
                       int likes, boolean userLiked) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.image_url = image_url;
        this.likes = likes;
        this.userLiked = userLiked;
    }

    public PostRequest() {}

    public PostRequest(int id, String image_url, String content) {
        this.id = id;
        this.image_url = image_url;
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }


    public boolean isUserLiked() {
        return userLiked;
    }

    public void setUserLiked(boolean userLiked) {
        this.userLiked = userLiked;
    }


    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}