package org.example.serversidesocialnetworkemo.controller;

import org.example.serversidesocialnetworkemo.DataBase.DBManager;
import org.example.serversidesocialnetworkemo.Request.PostRequest;
import org.example.serversidesocialnetworkemo.Request.*;
import org.example.serversidesocialnetworkemo.Entity.User;
import org.example.serversidesocialnetworkemo.Response.BasicResponse;
import org.example.serversidesocialnetworkemo.Response.CommentResponse;
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
    public PostRequest addPosts(@RequestBody CreatePostRequest postRequest , @RequestHeader("Authorization") String token){
        PostRequest post = null;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null && postRequest != null && (postRequest.getImageUrl() != null && !postRequest.getImageUrl().trim().isEmpty() ||(postRequest.getContent()!=null && !postRequest.getContent().trim().isEmpty()))){
            post = this.dbManager.addPosts(userId,postRequest.getImageUrl(),postRequest.getContent());
        }
        return post;
    }

    @GetMapping("/get-my-posts")
    public List<PostRequest> getPosts(@RequestHeader("Authorization") String token){
        List<PostRequest> postRequests = null;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null) {
            postRequests = this.dbManager.getPostsByUserId(userId);
        }
        return postRequests;
    }
    @GetMapping("/get-all-username")
    public List<UserHeaderResponse> getAllUsername(){
        return this.dbManager.getAllUsername();
    }


    @GetMapping("/getPost/{id}")
    public PostRequest getPost (@PathVariable int id, @RequestHeader ("Authorization") String token){
        PostRequest postRequest = null;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null){
            postRequest = this.dbManager.getPostId(id,userId);
        }
        return postRequest;
    }

    @PostMapping("/like")
    public int toggleLike(@RequestHeader("Authorization") String token , @RequestBody LikeRequest likeRequest){
            int likes = 0;
            Integer userId = this.dbManager.getUserIdByToken(token);
            if (userId != null){
               if (this.dbManager.existLike(userId,likeRequest.getPostId())){
                   this.dbManager.removeLike(userId,likeRequest.getPostId());
               } else{
                    this.dbManager.addLike(userId,likeRequest.getPostId());
               }
                likes = this.dbManager.countLike(likeRequest.getPostId());
            }
            return likes;
    }
    @GetMapping("/get-user-page/{id}")
    public UserProfile getUserPage(@PathVariable int id ,@RequestHeader("Authorization") String token) {
        String username = null;
        String profilePicUrl = null;
        int postsCount = 0;
        List<String> follower = new ArrayList<>();
        List<String> following = new ArrayList<>();
        Integer userId = this.dbManager.getUserIdByToken(token);
        boolean isFollowing = false;
        if (userId != null) {
            username = this.dbManager.getUsername(id);
            profilePicUrl = this.dbManager.getByProfile(id);
            postsCount = this.dbManager.getPostCount(id);
            follower = this.dbManager.getFollowerName(id);
            following = this.dbManager.getFollowingName(id);
            isFollowing = this.dbManager.doIsFollowing( userId,id);
        }
        return new UserProfile(username,profilePicUrl,postsCount,follower,following,isFollowing);
    }
    @GetMapping("/get-user-post/{id}")
    public List<PostRequest> getPostByUser(@PathVariable int id , @RequestHeader("Authorization") String token){
        List<PostRequest> postRequests = new ArrayList<>();
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null){
            postRequests = this.dbManager.getPostsByUserId(id);
        }
        return postRequests;
    }
    @GetMapping("/getComments/{id}")
    public List<CommentResponse> getComments(@PathVariable int id ,@RequestHeader("Authorization") String token){
        List<CommentResponse> commentResponses = new ArrayList<>();
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null){
            commentResponses = this.dbManager.getComments(id);
        }
        return commentResponses;
    }
    @PostMapping("addComment")
    public void addComment(@RequestHeader("Authorization") String token , @RequestBody AddCommentRequest addCommentRequest){
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null) {
            this.dbManager.addComments(addCommentRequest.getPostId(),userId,addCommentRequest.getContent());
        }
    }
    @PostMapping("/followingClick/{id}")
    public BasicResponse followingClick(@PathVariable int id ,@RequestHeader("Authorization") String token){
        boolean success = false;
        Integer errorCode = Errors.ERROR_FOLLOWING_CLICK;
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null && userId != id){
            if (this.dbManager.doIsFollowing( userId,id)){
                this.dbManager.removeFollowing(userId,id);
            }else {
                this.dbManager.addFollowing(userId,id);
            }
            success = true;
            errorCode = null;
         }
        return new BasicResponse(success,errorCode);
    }
    @GetMapping("/feed")
    public List<PostRequest> getFeed(@RequestHeader("Authorization") String token){
        List<PostRequest> posts = new ArrayList<>();
        Integer userId = this.dbManager.getUserIdByToken(token);
        if (userId != null){
            posts = this.dbManager.getFeed(userId);
        }
        return posts;
    }

}