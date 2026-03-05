package org.example.serversidesocialnetworkemo.Response;

public class PostResponse {
    private int id;
    private String imageUrl;
    private int likeCount;
    private int commentCount;




    public PostResponse(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public PostResponse(int id, String imageUrl, int likeCount, int commentCount) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
