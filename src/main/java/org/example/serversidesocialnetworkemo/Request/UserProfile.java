package org.example.serversidesocialnetworkemo.Request;

import java.util.List;

public class UserProfile {
 private int userId;
 private String username;
 private String profileUrl;
 private int postsCount;
 private List<String> followersCount;
 private List<String> followingCount;
 private boolean isFollowing;


 public UserProfile(){}


//followers = עוקבים שלי
//following = אנשים שאני עוקבת אחריהם

    public UserProfile( String username, String profileUrl, int postsCount, List<String> followersCount, List<String> followingCount, boolean isFollowing) {
        this.username = username;
        this.profileUrl = profileUrl;
        this.postsCount = postsCount;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.isFollowing = isFollowing;
    }


    public UserProfile(String username, String profileUrl, int postsCount, List<String> followersCount,  List<String> followingCount) {
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

    public  List<String> getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount( List<String> followersCount) {
        this.followersCount = followersCount;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public List<String> getFollowingCount() {
        return followingCount;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFollowingCount(List<String> followingCount) {
        this.followingCount = followingCount;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }
}
