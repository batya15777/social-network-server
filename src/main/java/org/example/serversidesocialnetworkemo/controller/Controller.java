package org.example.serversidesocialnetworkemo.controller;

import org.example.serversidesocialnetworkemo.DataBase.DBManager;
import org.example.serversidesocialnetworkemo.Entity.Like;
import org.example.serversidesocialnetworkemo.Entity.Post;
import org.example.serversidesocialnetworkemo.Request.*;
import org.example.serversidesocialnetworkemo.Entity.User;
import org.example.serversidesocialnetworkemo.Response.BasicResponse;
import org.example.serversidesocialnetworkemo.Response.LoginResponse;
import org.example.serversidesocialnetworkemo.Response.UserHeaderResponse;
import org.example.serversidesocialnetworkemo.Utils.Errors;
import org.example.serversidesocialnetworkemo.Utils.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController



public class Controller {


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
        } else if ( user.getUsername() ==null||user.getUsername().trim().isEmpty()) {
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
        List<String> follower = new ArrayList<>();
        List<String> following = new ArrayList<>();
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null) {
            username = this.dbManager.getUsername(userId);
            profilePicUrl = this.dbManager.getByProfile(userId);
            postsCount = this.dbManager.getPostCount(userId);
            follower = this.dbManager.getFollowerName(userId);
            following = this.dbManager.getFollowingName(userId);

        }
        return new UserProfile(username, profilePicUrl, postsCount, follower, following);

    }

    @PostMapping("/update-profile")
    public  BasicResponse updateProfile (@RequestBody UpdateProfileRequest urlProfile, @RequestHeader("Authorization") String token) {
        boolean success = false;
        Integer errorCode = Errors.ERROR_UPDATE_PROFILE;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null &&  urlProfile != null && urlProfile.getProfileUrl()!= null && !urlProfile.getProfileUrl().trim().isEmpty()  ) {
           if (this.dbManager.updateProfileByUser(userId,urlProfile.getProfileUrl())){
               success = true;
               errorCode = null;
           }
        }
        return new BasicResponse(success,errorCode);

    }

    @PostMapping("/add-posts")
    public Post addPosts(@RequestBody CreatePostRequest postRequest , @RequestHeader("Authorization") String token){
        Post post = null;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null && postRequest != null && (postRequest.getImageUrl() != null && !postRequest.getImageUrl().trim().isEmpty() ||(postRequest.getContent()!=null && !postRequest.getContent().trim().isEmpty()))){
            post = this.dbManager.addPosts(userId,postRequest.getImageUrl(),postRequest.getContent());
        }
        return post;
    }

    @GetMapping("/get-my-posts")
    public List<Post> getPosts( @RequestHeader("Authorization") String token){
        List<Post> posts = null;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null) {
            posts = this.dbManager.getPostsByUserId(userId);
        }
        return posts;
    }
    @GetMapping("/get-all-username")
    public List<UserHeaderResponse> getAllUsername(){
        return this.dbManager.getAllUsername();
    }

    @GetMapping("/getPost/{id}")
    public Post getPost (@PathVariable int id,@RequestHeader ("Authorization") String token){
        Post post = null;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null){
            post = this.dbManager.getPostId(id,userId);
        }
        return post;
    }

    @PostMapping("/like")
    public int toggleLike(@RequestHeader("Authorization") String token , @RequestBody LikeRequest likeRequest){
            int like = 0;
            Integer userId = this.dbManager.getUserIdByToken(token);
            if (userId != null){
               if (this.dbManager.existLike(userId,likeRequest.getPostId())){
                   this.dbManager.removeLike(userId,likeRequest.getPostId());
               } else{
                    this.dbManager.addLike(userId,likeRequest.getPostId());
               }
                like = this.dbManager.countLike(likeRequest.getPostId());
            }
            return like;
    }
    @GetMapping("/get-user-page/{id}")
    public UserProfile getUserPage(@PathVariable int id ,@RequestHeader("Authorization") String token) {
        String username = null;
        String profilePicUrl = null;
        int postsCount = 0;
        List<String> follower = new ArrayList<>();
        List<String> following = new ArrayList<>();
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null) {
            username = this.dbManager.getUsername(id);
            profilePicUrl = this.dbManager.getByProfile(id);
            postsCount = this.dbManager.getPostCount(id);
            follower = this.dbManager.getFollowerName(id);
            following = this.dbManager.getFollowingName(id);

        }
        return new UserProfile(username,profilePicUrl,postsCount,follower,following);
    }
    @GetMapping("/get-user-post/{id}")
    public List<Post> getPostByUser(@PathVariable int id ,@RequestHeader("Authorization") String token){
        List<Post> posts = new ArrayList<>();
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null){
            posts = this.dbManager.getPostsByUserId(id);
        }
        return posts;
    }
}