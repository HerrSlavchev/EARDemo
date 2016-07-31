/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.service;

import com.myoldbooks.service.security.Hasher;
import com.myoldbooks.utils.CSRFMode;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import static com.myoldbooks.utils.Configs.*;

/**
 *
 * @author SRVR1
 */
@Stateless
public class CSRFService {
    
    @Inject
    private Hasher hasher;
    
    @Inject 
    SessionContext sessionContext;
    
    /**
     * If Config.CSRF_MODE is set to TOKEN_PER_REQUEST, or there is no token or the token is empty ,
     * a new value for the CSRF token will be generated and set for the session.
     */
    public String refreshToken(){
        Object oldToken = sessionContext.getContextData().get(KEY_CSRF_TOKEN);
        String token = oldToken == null ? null : oldToken.toString();
        if (CSRF_MODE == CSRFMode.TOKEN_PER_REQUEST 
                || sessionContext.getContextData().get(KEY_CSRF_TOKEN) == null
                || ((String) sessionContext.getContextData().get(KEY_CSRF_TOKEN)).isEmpty()) {
            token = hasher.generateRandomString(64);
            sessionContext.getContextData().put(KEY_CSRF_TOKEN, token);
        }    
        return token;
    }
    
    public String getToken() {
        Object token = sessionContext.getContextData().get(KEY_CSRF_TOKEN);
        return token == null ? null : (String) token;
    }
    
    public boolean checkToken(String token){
        return token == null ? false : token.equals(sessionContext.getContextData().get(KEY_CSRF_TOKEN));
    }
    
    public void clearToken() {
        sessionContext.getContextData().remove(KEY_CSRF_TOKEN);
    }
}
