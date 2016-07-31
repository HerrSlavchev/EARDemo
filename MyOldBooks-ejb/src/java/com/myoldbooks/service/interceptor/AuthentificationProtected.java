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
public @interface AuthentificationProtected {
    
    /**
     * GUEST - invoker of the request must not be logged in
     * USER - the request is allowed only for logged in users
     * ANY - the request can be invoked by anyone
     */
    public enum Role {
        GUEST, USER, ANY
    }
    
    Role role() default Role.USER; 
}
