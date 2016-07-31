/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.facade;

import com.myoldbooks.model.Result;
import com.myoldbooks.model.User;
import com.myoldbooks.service.CSRFService;
import com.myoldbooks.service.UserService;
import com.myoldbooks.service.interceptor.AuthentificationProtected;
import com.myoldbooks.service.interceptor.CSRFProtected;
import com.myoldbooks.service.interceptor.CSRFToken;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Default: methods will require the user to be logged in and provide a CSRF token (mark with @CSRFToken).
 * @author SRVR1
 */
@Stateless
@CSRFProtected
@AuthentificationProtected
public class ServiceFacade {

    @Inject
    UserService userService;

    @Inject
    CSRFService csrfService;

    @CSRFProtected(challangeType = CSRFProtected.ChallengeStrategy.NONE)
    @AuthentificationProtected(role = AuthentificationProtected.Role.GUEST)
    public Result<String> login(String nickname, String password) {
        
        Result<String> res = new Result<>();
        
        User u = userService.loginUser(nickname, password).getRes();
        if (u != null && u.getId() != 0) {
            String token = csrfService.refreshToken();
            res.setRes(token);
        } else {
            res.setErrMsg("Invalid username or password!");
        }

        return res;
    }
    
    public Result<String> logout(@CSRFToken String token) {
        return userService.logoutUser();
    }

    @AuthentificationProtected(role = AuthentificationProtected.Role.GUEST)
    public Result<String> registerUser(User user, String pass) {
        return userService.registerUser(user, pass);
    }
    
    
}
