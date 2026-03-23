package org.example.serversidesocialnetworkemo.Request;

public class CreatePostRequest {
    private String imageUrl;
    private String content;




    public CreatePostRequest(){}
    public CreatePostRequest(String imageUrl, String content) {
        this.imageUrl = imageUrl;
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String text) {
        this.content = text;
    }
}
