/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.service;

import com.myoldbooks.facade.ServiceFacade;
import com.myoldbooks.model.Result;
import com.myoldbooks.model.User;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author SRVR1
 */
@Path("/")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestFacade {
    
    @Inject
    private ServiceFacade serviceFacade;
    
    @POST
    @Path("/register")
    public Result<String> registerUser(User user, String pass) {
        return serviceFacade.registerUser(user, pass);
    }
    
    @POST
    @Path("/login")
    public Result<String> loginUser(String nickname, String pass) {
        return serviceFacade.login(nickname, pass);
    }
    
}
