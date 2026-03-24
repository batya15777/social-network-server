package org.example.serversidesocialnetworkemo.Request;

public class AddCommentRequest {


        private int postId;
        private String content;

        public AddCommentRequest(){}

    public AddCommentRequest(int postId, String content) {
        this.postId = postId;
        this.content = content;
    }

    public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

