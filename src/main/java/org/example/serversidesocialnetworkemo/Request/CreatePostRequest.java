package org.example.serversidesocialnetworkemo.Request;

public class CreatePostRequest {
    private String imageUrl;




    public CreatePostRequest(){}

    public CreatePostRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
