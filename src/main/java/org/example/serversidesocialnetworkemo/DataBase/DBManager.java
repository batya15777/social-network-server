package org.example.serversidesocialnetworkemo.DataBase;

import jakarta.annotation.PostConstruct;
import org.example.serversidesocialnetworkemo.Request.PostRequest;
import org.example.serversidesocialnetworkemo.Entity.User;
import org.example.serversidesocialnetworkemo.Response.CommentResponse;
import org.example.serversidesocialnetworkemo.Response.UserHeaderResponse;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBManager {
    private static final String UEL = "jdbc:mysql://localhost:3306/social_network";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private Connection connection;


    @PostConstruct
    public void connect() {

        try {
            connection = DriverManager.getConnection(UEL, USERNAME, PASSWORD);
            System.out.println("DB connected successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public boolean getUserByUsernamePassword(String username, String password) {
        boolean isExist = false;
        String sql = "SELECT username,password FROM users WHERE username =? AND password =?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExist = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;


    }


    public boolean isUserExist(String username) {
        boolean isExist = false;
        String sql = "SELECT username FROM users WHERE username =?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExist = true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public boolean addUser(User user) {
        boolean success = true;
        String sql = "INSERT INTO users (name, lastName, phone, generalSex, username, password,token,profileUrl) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getGeneralSex());
            preparedStatement.setString(5, user.getUsername());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getToken());
            preparedStatement.setString(8, user.getProfileUrl());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        }
        return success;
    }


    public boolean updateUserToken(String username, String token) {
        boolean success = false;
        String sql = "UPDATE users SET token = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public Integer getUserIdByToken(String token) {
        Integer userId = null;
        String sql = "SELECT id FROM users WHERE token = ? ";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public String getUsername(Integer userId) {
        String username = null;
        String sql = "SELECT username FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                username = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    public String getByProfile(Integer userId) {
        String profileUrl = null;
        String sql = "SELECT profileUrl FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                profileUrl = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profileUrl;
    }

    public int getPostCount(Integer userId) {
        int postCount = 0;
        String sql = "SELECT COUNT(*) FROM posts WHERE user_id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                postCount = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postCount;
    }

    public List<String> getFollowerName(Integer userId) {
        List<String> follower = new ArrayList<>();
        String sql = "SELECT u.username FROM follows f JOIN users u ON f.follower_id = u.id WHERE f.followed_id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                follower.add(resultSet.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return follower;
    }

    public List<String> getFollowingName(Integer userId) {
        List<String> following = new ArrayList<>();
        String sql = "SELECT u.username FROM follows f JOIN users u ON f.follower_id = u.id  WHERE f.follower_id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                following.add(resultSet.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return following;
    }
    public boolean doIsFollowing(Integer userId , int id){
        boolean isFollowing = false;
        String sql = "SELECT follower_id,followed_id FROM follows WHERE follower_id =? AND followed_id =? ";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isFollowing = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
         return isFollowing;
    }

    public boolean updateProfileByUser(int userId, String profileUrl) {
        boolean success = true;
        String sql = "UPDATE users SET profileUrl = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, profileUrl);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        }
        return success;

    }

    public PostRequest addPosts(int userId, String image_url, String content) {
        PostRequest post = null;
        String sql = "INSERT INTO posts(user_id,image_url,content) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, image_url);
            preparedStatement.setString(3, content);
            preparedStatement.executeUpdate();
            post = new PostRequest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public List<PostRequest> getPostsByUserId(int userId) {
        List<PostRequest> postRequests = new ArrayList<>();
        String sql = "SELECT id , image_url,content,created_at FROM posts WHERE user_id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PostRequest p = new PostRequest(resultSet.getInt("id"), resultSet.getString("image_url"), resultSet.getString("content"));
                postRequests.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postRequests;
    }

    public List<UserHeaderResponse> getAllUsername() {
        List<UserHeaderResponse> userHeaderResponse = new ArrayList<>();
        String sql = "SELECT id,username,profileUrl FROM users";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserHeaderResponse u = new UserHeaderResponse(resultSet.getInt("id"),resultSet.getString("profileUrl"), resultSet.getString("username"));
                userHeaderResponse.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userHeaderResponse;
    }

    public PostRequest getPostId(int id, int userId) {
        PostRequest postRequest = null;
        String sql = "SELECT id,content,created_at,image_url FROM posts WHERE id =?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int likes = countLike(id);
                 likes= countLike(id);
                boolean userLike = existLike(userId,id);
                int comment = countComments(id);
                postRequest = new PostRequest(
                        resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getDate("created_at"),
                        resultSet.getString("image_url"),
                        likes,
                        userLike,
                        comment
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postRequest;

    }

    public boolean existLike(int userId, int postId) {
        boolean isExist = false;
        String sql = "SELECT user_id,post_id FROM likes WHERE user_id = ? AND post_id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExist = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public void removeLike(int userId, int postId) {
        String sql = "DELETE FROM likes WHERE user_id = ? AND post_id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addLike(int userId, int postId) {
        String sql = "INSERT INTO  likes(user_id,post_id) VALUES (?,?) ";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public int countLike( int postId){
        int like = 0;
        String sql = "SELECT COUNT(*) FROM likes WHERE  post_id = ? ";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              like = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return like;
    }
    public int countComments( int postId){
        int comment = 0;
        String sql = "SELECT COUNT(*) AS comments FROM comments WHERE post_id =?  ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                comment = resultSet.getInt("comments");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comment;
    }


    public List<CommentResponse>getComments(int id){
        List<CommentResponse> comments = new ArrayList<>();
        String sql = "SELECT c.id,c.content,c.created_at,u.username,u.profileUrl From comments c JOIN users u ON c.user_id = u.id WHERE post_id =? ORDER BY c.created_at";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                CommentResponse c = new CommentResponse(resultSet.getString("username"),
                        resultSet.getString("profileUrl"),
                        resultSet.getString("content"),
                        resultSet.getDate("created_at"));
                        comments.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
              return comments;
    }
    public void addComments(int postId,int userId,String content){
        String sql = "INSERT INTO comments (post_id,user_id,content) VALUES ( ?, ?,? )";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,postId);
            preparedStatement.setInt(2,userId);
            preparedStatement.setString(3,content);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeFollowing(int userId, int id){
        String sql = "DELETE FROM follows WHERE follower_id = ? AND followed_id = ?";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addFollowing(int userId, int id){
        String sql = "INSERT INTO  follows(follower_id,followed_id) VALUES (?,?) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<PostRequest> getFeed(int userId){
        List<PostRequest> posts = new ArrayList<>();
        String sql = "SELECT p.id ,p.content ,p.created_at ,p.image_url, u.username ,u.profileUrl "+
                "FROM posts p JOIN follows f ON p.user_id = f.followed_id " +
                "JOIN users u ON p.user_id = u.id "+
                "WHERE f.follower_id = ? "+
                "ORDER BY p.created_at DESC LIMIT 20";
        try(PreparedStatement p = this.connection.prepareStatement(sql)){
            p.setInt(1,userId);
            ResultSet rs = p.executeQuery();
            while (rs.next()){
                //String username, String profileUrl, String content, int id, String image_url, Date createAt, int likes, int comments
                PostRequest post = new PostRequest(rs.getString("username"),rs.getString("profileUrl")
                        ,rs.getString("content"),rs.getInt("id"),rs.getString("image_url"),rs.getDate("created_at")
                        ,countLike(rs.getInt("id")),countComments(rs.getInt("id")));
                posts.add(post);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return posts;
    }



    }







