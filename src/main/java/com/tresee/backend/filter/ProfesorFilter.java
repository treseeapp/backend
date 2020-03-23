package com.tresee.backend.filter;

import com.tresee.backend.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfesorFilter implements HandlerInterceptor {
    @Autowired
    TokenManager tokenManager;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
         * Detecta si la petición es un OPTIONS en tal caso devuelve true.
         * */
        if (request.getMethod().equals("OPTIONS")) return true;

        /*
         * Si no es un OPTIONS comprueba si la petición contiene el Token
         * y comprueba si es válido o si ha expirado.
         * */
        String auth = request.getHeader("Authorization");

        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            // TODO mirar de hacer un cast al enum rol desde el valor que nos llega
            String rol=tokenManager.getRol(token);

            boolean isProfesor = true;

            // TODO COMPROBAR SI EL VALOR LLEGA EN STRING O EN INTEGER
            if (!rol.equals("PROFESOR")) isProfesor=false;

            if (isProfesor) {
                return true;
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permisos para esta accion");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no recibido");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
