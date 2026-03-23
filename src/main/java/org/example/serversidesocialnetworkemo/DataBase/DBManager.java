package org.example.serversidesocialnetworkemo.DataBase;

import jakarta.annotation.PostConstruct;
import org.example.serversidesocialnetworkemo.Entity.Like;
import org.example.serversidesocialnetworkemo.Entity.Post;
import org.example.serversidesocialnetworkemo.Entity.User;
import org.example.serversidesocialnetworkemo.Response.UserHeaderResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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

    public Post addPosts(int userId, String image_url, String content) {
        Post post = null;
        String sql = "INSERT INTO posts(user_id,image_url,content) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, image_url);
            preparedStatement.setString(3, content);
            preparedStatement.executeUpdate();
            post = new Post();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public List<Post> getPostsByUserId(int userId) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT id , image_url,content,created_at FROM posts WHERE user_id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Post p = new Post(resultSet.getInt("id"), resultSet.getString("image_url"), resultSet.getString("content"));
                posts.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
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

    public Post getPostId(int id,int userId) {
        Post post = null;
        String sql = "SELECT id,content,created_at,image_url FROM posts WHERE id =?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int like = countLike(id);
                boolean userLike = existLike(userId,id);
                post = new Post(
                        resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getDate("created_at"),
                        resultSet.getString("image_url"),
                        like,
                        userLike);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;

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



}




