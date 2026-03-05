package org.example.serversidesocialnetworkemo.Request;

public class UserProfile {
 private int userId;
 private String username;
 private String profileUrl;
 private int postsCount;
 private int followersCount;
 private int followingCount;




 public UserProfile(){}


//followers = עוקבים שלי
//following = אנשים שאני עוקבת אחריהם

    public UserProfile(String username, String profileUrl, int postsCount, int followersCount, int followingCount) {
        this.username = username;
        this.profileUrl = profileUrl;
        this.postsCount = postsCount;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profilePicUrl) {
        this.profileUrl = profilePicUrl;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }


}
