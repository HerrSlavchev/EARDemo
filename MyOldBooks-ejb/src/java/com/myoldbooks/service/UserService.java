/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.service;

import com.myoldbooks.model.Cred;
import com.myoldbooks.model.Result;
import com.myoldbooks.model.User;
import com.myoldbooks.service.security.Hasher;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import static com.myoldbooks.utils.Configs.*;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author SRVR1
 */
@SessionScoped
public class UserService extends JPAService<User> implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private User user = null;
    
    @Inject
    private Hasher hasher;
        
    public UserService() {
        super(User.class);
    }
    
    
    public Result<User> loginUser(String nickname, String pass) {
        
        Result<User> res = new Result();
        
        User u = find("User.findByNickname", "nickname", nickname);
        if(u != null && u.getId() != 0){
            if(hasher.verifyPass(u.getCred(), pass)) {
                user = u;
                res.setRes(u);
            } else {
                res.setErrMsg("Invalid username/password combination!");
            }
        } else {
            res.setErrMsg("Invalid username/password combination!");
        }
        
        return res;
    }
    
    public Result<String> registerUser(User user, String pass) {
        
        Result<String> res = new Result();
        
        User oldUser = find("User.findByNickname", "nickname", user.getNickname());
        if(oldUser == null || oldUser.getId() == 0) {
            Cred cred = hasher.generateCred(pass);
            user.setCred(cred);
            create(user);
            res.setRes("Successful registration!");
        } else {
            res.setErrMsg("User nickname already exists.");
        }
        
        return res;
    }
    
    public Result<String> logoutUser(){
        Result<String> res = new Result();
        user = null;
        //TODO: check what else should be cleaned up
        res.setRes("Successful logout!");
        return res;
    }
    
    public boolean isLogged(){
        return user != null && user.getId() != 0;
    }
    
}
