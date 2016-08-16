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
import com.myoldbooks.service.interceptor.FacadeInterceptor;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

/**
 * Default: methods will require the user to be logged in and provide a CSRF token (mark with @CSRFToken).
 * @author SRVR1
 */
@Stateless
@CSRFProtected
@AuthentificationProtected
@Interceptors(FacadeInterceptor.class)
public class ServiceFacade {

    @Inject
    UserService userService;

    @Inject
    CSRFService csrfService;

    @CSRFProtected(challengeType = CSRFProtected.ChallengeStrategy.NONE)
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
    
    @AuthentificationProtected(role = AuthentificationProtected.Role.USER)
    @CSRFProtected(challengeType = CSRFProtected.ChallengeStrategy.REQUIRED)
    public Result<String> logout(@CSRFToken String token) {
        csrfService.clearToken();
        return userService.logoutUser();
    }

    @AuthentificationProtected(role = AuthentificationProtected.Role.GUEST)
    @CSRFProtected(challengeType = CSRFProtected.ChallengeStrategy.NONE)
    public Result<String> registerUser(User user, String pass) {
        return userService.registerUser(user, pass);
    }
    
}
