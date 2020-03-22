package com.tresee.backend.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenFilter implements HandlerInterceptor {

    @Autowired
    TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String auth = request.getHeader("Authorization");

        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenManager.validateToken(token);

            if (validate.equals("ERROR")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;

            } else if (validate.equals("EXPIRED")) {
                response.sendError(403, "EXPIRED");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                return true;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
