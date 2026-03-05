package org.example.serversidesocialnetworkemo.Entity;

import javax.xml.crypto.Data;

public class Post {
    private int id;
    private int User_id;
    private String content;
    private Data creat_at;
    private String image_url;






    public Post(){}
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

    public Data getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(Data creat_at) {
        this.creat_at = creat_at;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
