/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.service.interceptor;

import com.myoldbooks.model.Result;
import com.myoldbooks.service.CSRFService;
import com.myoldbooks.service.UserService;
import java.lang.annotation.Annotation;
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
        Object res = null;
        if (checkCSRF(context) && checkSession(context)) {
            res = context.proceed();
        } else {
            res = new Result<>();
            ((Result) res).setErrMsg("Invalid authentication!");
        }

        return res;
    }

    private boolean checkCSRF(InvocationContext context) throws Exception {
        CSRFProtected.ChallengeStrategy csrfStrategy = CSRFProtected.ChallengeStrategy.ANY;
        CSRFProtected csrfAnno = getAnnotationByType(context, CSRFProtected.class);
        if (csrfAnno != null) {
            csrfStrategy = csrfAnno.challengeType();
        }

        if (csrfStrategy == CSRFProtected.ChallengeStrategy.REQUIRED) {
            Parameter[] params = context.getMethod().getParameters();
            Object[] paramValues = context.getParameters();
            for (int i = 0; i < params.length; i++) {
                Parameter par = params[i];
                if (par.getAnnotation(CSRFToken.class) != null) {
                    String token = (String) paramValues[i];
                    if (!csrfService.checkToken(token)) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    private boolean checkSession(InvocationContext context) throws Exception {
        AuthentificationProtected.Role role = AuthentificationProtected.Role.ANY;
        AuthentificationProtected anno = getAnnotationByType(context, AuthentificationProtected.class);
        if (anno != null) {
            role = anno.role();
        }

        if ((role == AuthentificationProtected.Role.USER && !userService.isLogged())) {
            return false;
        } else if (role == AuthentificationProtected.Role.GUEST && userService.isLogged()) {
            return false;
        }
        return true;
    }

    private <T extends Annotation> T getAnnotationByType(InvocationContext context, Class<T> annoClass) {
        T[] annos = context.getMethod().getDeclaredAnnotationsByType(annoClass);
        if (annos.length == 0) {
            annos = context.getClass().getAnnotationsByType(annoClass);
        }

        return annos.length != 0 ? annos[0] : null;
    }

}
