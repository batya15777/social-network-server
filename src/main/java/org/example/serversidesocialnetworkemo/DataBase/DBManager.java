package org.example.serversidesocialnetworkemo.DataBase;

import jakarta.annotation.PostConstruct;
import org.example.serversidesocialnetworkemo.Entity.Post;
import org.example.serversidesocialnetworkemo.Entity.User;
import org.example.serversidesocialnetworkemo.Response.PostResponse;
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
        boolean success = true;
        String sql = "UPDATE users SET token = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            success = false;
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

    public int getFollowersCount(Integer userId) {
        int followersCount = 0;
        String sql = "SELECT COUNT(*) FROM follows WHERE followed_id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                followersCount = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followersCount;
    }

    public int getFollowingCount(Integer userId) {
        int followingCount = 0;
        String sql = "SELECT COUNT(*) FROM follows WHERE follower_id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                followingCount = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followingCount;
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

    public PostResponse addPosts(int userId, String postUrl) {
        PostResponse postResponse = null;
        String sql = "INSERT INTO posts(user_id,image_url) VALUES(?,?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, postUrl);
            preparedStatement.executeUpdate();
            postResponse = new PostResponse(postUrl);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postResponse;


    }
    public List<PostResponse> getMyPosts(int userId){
        List<PostResponse> posts = new ArrayList<>();
        String sql = "SELECT id , image_url FROM posts WHERE user_id = ?";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                PostResponse p = new PostResponse(resultSet.getInt("id"),
                        resultSet.getString("image_url"),
                        0,0
                        );
                   posts.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

}