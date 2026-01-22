package org.example.serversidesocialnetworkemo.controller;

import org.example.serversidesocialnetworkemo.DataBase.DBManager;
import org.example.serversidesocialnetworkemo.Entity.User;
import org.example.serversidesocialnetworkemo.Request.LoginRequest;
import org.example.serversidesocialnetworkemo.Response.BasicResponse;
import org.example.serversidesocialnetworkemo.Response.LoginResponse;
import org.example.serversidesocialnetworkemo.Utils.Errors;
import org.example.serversidesocialnetworkemo.Utils.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController

public class Control {

    @Autowired
    private DBManager dbManager;


@PostMapping("/login")
    public BasicResponse userLoginTest(@RequestBody LoginRequest loginRequest ){
    boolean success = false;
    Integer errorCode = Errors.ERROR_LOGIN;
    String token = null;
    if (loginRequest == null) {
        errorCode = Errors.ERROR_LOGIN;
    }
     else if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()){
        errorCode = Errors.USERNAME_EMPTY;
     }
     else if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()){
        errorCode = Errors.PASSWORD_EMPTY;
     }
     else  {
        token = Hashing.hash(loginRequest.getUsername(),loginRequest.getPassword());
        boolean exist = dbManager.getUserByUsernamePassword(loginRequest.getUsername(),token);
        if (exist){
            success = true;
            errorCode =null;
        }

    }
    return new LoginResponse(success,errorCode,token);
}

@PostMapping("/signup-user")
public BasicResponse userSignupChek(@RequestBody User user){
    boolean success = false;
    Integer errorCode = Errors.ERROR_SIGNUP;
    if (user == null ){
         errorCode = Errors.ERROR_SIGNUP;
    }
    else if (user.getUsername().trim().isEmpty()){
        errorCode = Errors.USERNAME_EMPTY;
    }
//        לבדוק עם גם פב לעשות את אותם בדיקות של הregex כמו שעשיתי בצד לקוח
    else {
        if (!this.dbManager.isUserExist(user.getUsername())){
            user.setPassword(Hashing.hash(user.getUsername(),user.getPassword()));
            boolean ok = this.dbManager.addUser(user);
            if (ok){
                success = true;
                errorCode = null;
            }
        }
        else {
           errorCode = Errors.USERNAME_ALREADY_EXISTS;

        }


    }

         return new BasicResponse(success,errorCode);
}




















}
