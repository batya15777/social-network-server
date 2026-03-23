package org.example.serversidesocialnetworkemo.Entity;

import java.util.Date;
import java.util.List;

public class Post {
    private int id;
    private int User_id;
    private String content;
    private Date creat_at;
    private String image_url;
    private int like;
    private boolean userLiked;


    public Post(int id, String content, Date creat_at, String image_url, int like, boolean userLiked) {
        this.id = id;
        this.content = content;
        this.creat_at = creat_at;
        this.image_url = image_url;
        this.like = like;
        this.userLiked = userLiked;
    }

    public Post(){}

    public Post(int id, String image_url,String content) {
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

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(Date creat_at) {
        this.creat_at = creat_at;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isUserLiked() {
        return userLiked;
    }

    public void setUserLiked(boolean userLiked) {
        this.userLiked = userLiked;
    }
}
