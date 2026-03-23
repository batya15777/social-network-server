package org.example.serversidesocialnetworkemo.Entity;

import javax.xml.crypto.Data;

public class Like {
    private int user_id;
    private int post_id;
    private Data data;



    public Like(){}


    public Like(int user_id, int post_id, Data data) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.data = data;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
