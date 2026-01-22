package org.example.serversidesocialnetworkemo.DataBase;

import jakarta.annotation.PostConstruct;
import org.example.serversidesocialnetworkemo.Entity.User;
import org.springframework.stereotype.Component;

import java.sql.*;
@Component
public class DBManager {
  private static final String UEL = "jdbc:mysql://localhost:3306/social_network";
  private static final String USERNAME ="root";
  private static final String PASSWORD ="1234";
  private Connection connection;


//    לבדוק עם אביה למה שמים דווקא רת connect ב postconstruct
    @PostConstruct
    public void connect() {

        try {
            connection = DriverManager.getConnection(UEL,USERNAME,PASSWORD);
            System.out.println("DB connected successfully");
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public boolean getUserByUsernamePassword(String username,String password){
        boolean isExist = false ;
        String sql = "SELECT username,password FROM users WHERE username =? AND password =?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)){
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                isExist = true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return isExist;


    }


    public boolean isUserExist(String username){
        boolean isExist = false;
        String sql = "SELECT username FROM users WHERE username =?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                isExist = true;

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
                return isExist;
    }
    public boolean addUser(User user){
        boolean success = true;
        String sql = "INSERT INTO users (name, lastName, phone, generalSex, username, password) VALUES (?, ?, ?, ?, ?, ?)";
       try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
           preparedStatement.setString(1,user.getName());
           preparedStatement.setString(2,user.getLastName());
           preparedStatement.setString(3,user.getPhone());
           preparedStatement.setString(4,user.getGeneralSex());
           preparedStatement.setString(5, user.getUsername());
           preparedStatement.setString(6, user.getPassword());
           preparedStatement.executeUpdate();

       }catch (SQLException e){
           success = false;
           e.printStackTrace();
       }
          return success;
    }



}
