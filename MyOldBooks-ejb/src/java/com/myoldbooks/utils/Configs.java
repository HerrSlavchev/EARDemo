/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.utils;

/**
 *
 * @author SRVR1
 */
public class Configs {
    
    public static final String KEY_USER_SESSION = "user_in_session";
    public static final String KEY_CSRF_TOKEN = "csrf_token";
    
    public static final CSRFMode CSRF_MODE = CSRFMode.TOKEN_PER_SESSION;
}
