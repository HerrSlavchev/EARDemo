/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.service;

import com.myoldbooks.service.security.Hasher;
import com.myoldbooks.utils.CSRFMode;
import javax.ejb.SessionContext;
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
public class CSRFService implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private Hasher hasher;
    
    
    private String token = null;
    /**
     * If Config.CSRF_MODE is set to TOKEN_PER_REQUEST, or there is no token or the token is empty ,
     * a new value for the CSRF token will be generated and set for the session.
     * @return The current token
     */
    public String refreshToken(){
        if (CSRF_MODE == CSRFMode.TOKEN_PER_REQUEST 
                || token == null || token.isEmpty()) {
            token = hasher.generateRandomString(64);
        }    
        return token;
    }
    
    public String getToken() {
        return token;
    }
    
    public boolean checkToken(String token){
        String sessionToken = this.token;
        return token == null ? false : token.equals(sessionToken);
    }
    
    public void clearToken() {
        token = null;
    }
}
