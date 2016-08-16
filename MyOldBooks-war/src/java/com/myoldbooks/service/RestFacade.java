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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class RestFacade {

    @Inject
    private ServiceFacade serviceFacade;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Result<String> register(@FormParam("nickname") String nickname, @FormParam("fName") String fName, @FormParam("sName") String sName, @FormParam("lName") String lName,
            @FormParam("pass") String pass) {
        User user = new User();
        user.setNickname(nickname);
        user.setFName(fName);
        user.setSName(sName);
        user.setLName(lName);
        return serviceFacade.registerUser(user, pass);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Result<String> login(@FormParam("nickname") String nickname, @FormParam("pass") String pass) {
        return serviceFacade.login(nickname, pass);
    }

    @POST
    @Path("/logout")
    public Result<String> logout(@HeaderParam("csrf") String csrfToken){
        return serviceFacade.logout(csrfToken);
    }
    
    @GET
    @Path("/demo/unprotected")
    public Result<String> unrpotectedMethod(){
        return serviceFacade.unprotectedMethod();
    }
    
    @POST
    @Path("/demo/protected")
    public Result<String> protectedMethod(@HeaderParam("csrf") String csrfToken){
        return serviceFacade.protectedMethod(csrfToken);
    }
}
