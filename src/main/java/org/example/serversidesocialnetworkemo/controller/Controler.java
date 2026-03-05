package org.example.serversidesocialnetworkemo.controller;

import org.example.serversidesocialnetworkemo.DataBase.DBManager;
import org.example.serversidesocialnetworkemo.Request.CreatePostRequest;
import org.example.serversidesocialnetworkemo.Request.UpdateProfileRequest;
import org.example.serversidesocialnetworkemo.Entity.User;
import org.example.serversidesocialnetworkemo.Request.UserProfile;
import org.example.serversidesocialnetworkemo.Request.LoginRequest;
import org.example.serversidesocialnetworkemo.Response.BasicResponse;
import org.example.serversidesocialnetworkemo.Response.LoginResponse;
import org.example.serversidesocialnetworkemo.Response.PostResponse;
import org.example.serversidesocialnetworkemo.Utils.Errors;
import org.example.serversidesocialnetworkemo.Utils.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController



public class Controler {


    @Autowired
    private DBManager dbManager;


    @PostMapping("/login")

    public BasicResponse userLoginTest(@RequestBody LoginRequest loginRequest) {
        boolean success = false;
        Integer errorCode = Errors.ERROR_LOGIN;
        String token = null;

        if (loginRequest == null) {
            errorCode = Errors.ERROR_LOGIN;
        } else if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()) {
            errorCode = Errors.USERNAME_EMPTY;
        } else if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            errorCode = Errors.PASSWORD_EMPTY;
        } else {
            String hashedPassword = Hashing.hash(loginRequest.getUsername(), loginRequest.getPassword());
            if (dbManager.getUserByUsernamePassword(loginRequest.getUsername(), hashedPassword)) {
                token = java.util.UUID.randomUUID().toString();
                if (this.dbManager.updateUserToken(loginRequest.getUsername(), token)) {
                    success = true;
                    errorCode = null;
                } else {
                    success = false;
                    errorCode = Errors.ERROR_LOGIN;
                    token = null;
                }


            }

        }

        return new LoginResponse(success, errorCode, token);
    }

    @PostMapping("/signup-user")
    public BasicResponse userSignupChek(@RequestBody User user) {
        boolean success = false;
        Integer errorCode = Errors.ERROR_SIGNUP;
        if (user == null) {
            errorCode = Errors.ERROR_SIGNUP;
        } else if (user.getUsername().trim().isEmpty()) {
            errorCode = Errors.USERNAME_EMPTY;
        }
//        לבדוק עם גם פב לעשות את אותם בדיקות של הregex כמו שעשיתי בצד לקוח
        else {
            if (!this.dbManager.isUserExist(user.getUsername())) {
                user.setPassword(Hashing.hash(user.getUsername(), user.getPassword()));
                boolean ok = this.dbManager.addUser(user);
                if (ok) {
                    success = true;
                    errorCode = null;
                }
            } else {
                errorCode = Errors.USERNAME_ALREADY_EXISTS;

            }
        }


        return new BasicResponse(success, errorCode);
    }

    @GetMapping("/get-profile")
    public UserProfile getProfile(@RequestHeader("Authorization") String token) {
        String username = null;
        String profilePicUrl = null;
        int postsCount = 0;
        int followersCount = 0;
        int followingCount = 0;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null) {
            username = this.dbManager.getUsername(userId);
            profilePicUrl = this.dbManager.getByProfile(userId);
            postsCount = this.dbManager.getPostCount(userId);
            followersCount = this.dbManager.getFollowingCount(userId);
            followingCount = this.dbManager.getFollowersCount(userId);

        }
        return new UserProfile(username, profilePicUrl, postsCount, followersCount, followingCount);

    }

    @PostMapping("/update-profile")
    public boolean updateProfile(@RequestBody UpdateProfileRequest urlProfile, @RequestHeader("Authorization") String token) {
        boolean success = false;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null) {
           if (this.dbManager.updateProfileByUser(userId,urlProfile.getProfileUrl())){
               success = true;
           }
        }
        return success;

    }

    @PostMapping("/add-posts")
    public PostResponse addPosts(@RequestBody CreatePostRequest postUrl , @RequestHeader("Authorization") String token){
        PostResponse postResponse = null;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null && postUrl.getImageUrl() != null && !postUrl.getImageUrl().trim().isEmpty()){
            postResponse = this.dbManager.addPosts(userId,postUrl.getImageUrl());
        }

        return postResponse;


    }
    @GetMapping("/get-my-posts")
    public List<PostResponse> getPosts( @RequestHeader("Authorization") String token){
        List<PostResponse> postResponses = null;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null) {
            postResponses = this.dbManager.getMyPosts(userId);
        }
        return postResponses;
    }
}