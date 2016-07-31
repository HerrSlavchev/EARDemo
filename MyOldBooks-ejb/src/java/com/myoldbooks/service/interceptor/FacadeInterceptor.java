/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.service.interceptor;

import com.myoldbooks.service.CSRFService;
import com.myoldbooks.service.UserService;
import java.lang.reflect.Parameter;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author SRVR1
 */
public class FacadeInterceptor {
    
    @Inject
    CSRFService csrfService;
    
    @Inject 
    UserService userService;
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        
        CSRFProtected.ChallengeStrategy csrfStrategy = CSRFProtected.ChallengeStrategy.ANY;
        CSRFProtected[] csrfAnnos = context.getMethod().getDeclaredAnnotationsByType(CSRFProtected.class);
        if(csrfAnnos.length == 0) {
            csrfAnnos = context.getMethod().getAnnotationsByType(CSRFProtected.class);
        }
        if(csrfAnnos.length != 0) {
            csrfStrategy = csrfAnnos[0].challangeType();
        }
        
        if(csrfStrategy == CSRFProtected.ChallengeStrategy.REQUIRED){
            Parameter[] params = context.getMethod().getParameters();
            for(Parameter par : params) {
                if(par.getAnnotation(CSRFToken.class) != null){
                    String token = String.valueOf(par);
                    if(!csrfService.checkToken(token)) {
                        throw new RuntimeException("Incorrect CSRF Token!");
                    }
                }
            }
        }
        
        AuthentificationProtected.Role role = AuthentificationProtected.Role.ANY;
        AuthentificationProtected[] authAnnos = context.getMethod().getDeclaredAnnotationsByType(AuthentificationProtected.class);
        if(authAnnos.length == 0) {
            authAnnos = context.getMethod().getAnnotationsByType(AuthentificationProtected.class);
        }
        if(authAnnos.length != 0) {
            role = authAnnos[0].role();
        }
        
        if( (role == AuthentificationProtected.Role.USER && !userService.isLogged())) {
            throw new RuntimeException("User must log in!");
        } else if(role == AuthentificationProtected.Role.GUEST && userService.isLogged()) {
            throw new RuntimeException("User must log out!");
        }
        
        Object res = context.proceed();
        
        return res;
    }
}
