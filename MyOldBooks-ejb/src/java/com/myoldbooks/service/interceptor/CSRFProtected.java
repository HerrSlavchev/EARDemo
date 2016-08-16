/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.service.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author SRVR1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface CSRFProtected {
    
    /**
     * REQUIRED - there must be a match with the session CSRF token
     * NONE - no information about the token is allowed to be shared
     * ANY - no conditions
     */
    public enum ChallengeStrategy {
        REQUIRED, NONE, ANY
    }
    
    ChallengeStrategy challengeType() default ChallengeStrategy.REQUIRED; 
    
}
