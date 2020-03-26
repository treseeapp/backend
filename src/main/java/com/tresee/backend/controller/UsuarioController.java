package com.tresee.backend.controller;

import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.manager.TokenManager;
import com.tresee.backend.manager.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UsuarioController {
    @Autowired
    private UsuarioManager usuarioManager;

    @Autowired
    private TokenManager tokenManager;

    @GetMapping("/private/usuario")
    @Transactional
    public Usuario getMyInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");

        return tokenManager.getUsuarioFromToken(token);
    }

    @PutMapping("/private/usuario")
    @Transactional
    public ResponseEntity<String> updateMyInfo(@RequestBody String json, HttpServletRequest request) {

        /*
         * Cogemos el usuario del token, asi nos aseguramos de
         * que el usuario que se modifica es a si mismo
         * */
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario tokenUser = tokenManager.getUsuarioFromToken(token);

        Usuario recivedInfo = usuarioManager.fromJson(json);

        tokenUser.setNombre(recivedInfo.getNombre());
        tokenUser.setApellidos(recivedInfo.getApellidos());
        tokenUser.setDireccion(recivedInfo.getDireccion());
        tokenUser.setDataNacimiento(recivedInfo.getDataNacimiento());
        tokenUser.setGenero(recivedInfo.getGenero());

        usuarioManager.update(tokenUser);
        return new ResponseEntity<>("Información modificada correctamente", HttpStatus.OK);
    }


    @GetMapping("/private/usuario/foto")
    @Transactional
    public void getMyFoto(HttpServletRequest request) {
        /*
         * TODO este endpoint retornara URL foto amazon
         *  cogera el usuario del token
         *  despues cogera el nombre de foto
         *  pedira a amazon
         *  retornara url
         * */
    }
}
