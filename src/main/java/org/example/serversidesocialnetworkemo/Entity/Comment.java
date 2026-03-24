package org.example.serversidesocialnetworkemo.Entity;
import java.util.Date;

public class Comment {
    private int id;
    private int postId;
    private int userId;
    private Date created_at;



    public Comment(){}

    public Comment(int id, int postId, int userId, Date created_at) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return postId;
    }

    public void setPost_id(int post_id) {
        this.postId = post_id;
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
