package org.example.serversidesocialnetworkemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.example.serversidesocialnetworkemo.Errors.ERROR_LOGIN;

@CrossOrigin(origins = "http://localhost:5173")

@RestController

public class Control {
    @Autowired
    private DBManager dbManager;


@PostMapping("/login")
    public BasicResponse userLoginTest(@RequestBody LoginRequest loginRequest ){
    boolean success = false;
    Integer errorCode =Errors.ERROR_LOGIN;
    String token = null;
    if (loginRequest == null) {
        errorCode = Errors.ERROR_LOGIN;

    }
     else if (loginRequest.getUsername() == null ||loginRequest.getUsername().trim().isEmpty()){
        errorCode = Errors.USERNAME_EMPTY;
     }
     else if (loginRequest.getPassword() == null ||loginRequest.getPassword().trim().isEmpty()){
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
    if (user!= null && user.getUsername()!=null){
//        לבדוק עם גם פב לעשות את אותם בדיקות של הregex כמו שעשיתי בצד לקוח
        if (!this.dbManager.isUserExist(user.getUsername())){
            user.setPassword(Hashing.hash(user.getUsername(),user.getPassword()));
            if (this.dbManager.addUser(user)){
                success = true;
                errorCode = null;
            }


        }

    }
         return new BasicResponse(success,errorCode);
}




















}
