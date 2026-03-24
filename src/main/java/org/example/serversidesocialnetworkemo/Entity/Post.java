package org.example.serversidesocialnetworkemo.Entity;

import java.util.Date;

public class Post {

    private int id;
    private int userId;
    private String content;
    private Date createAt;
    private String image_url;
    private int likes;
    private boolean userLiked;
    private int comments;

    public Post(int id, String content, Date createAt, String image_url,
                int likes, boolean userLiked, int comments) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.image_url = image_url;
        this.likes = likes;
        this.userLiked = userLiked;
        this.comments = comments;
    }

    public Post(int id, String content, Date createAt, String image_url,
                int likes, boolean userLiked) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.image_url = image_url;
        this.likes = likes;
        this.userLiked = userLiked;
    }

    public Post() {}

    public Post(int id, String image_url, String content) {
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


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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