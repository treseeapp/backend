package com.tresee.backend.controller;

import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.manager.AmazonManager;
import com.tresee.backend.manager.TokenManager;
import com.tresee.backend.manager.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioManager usuarioManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private AmazonManager amazonManager;

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

        /*
        * Antes de modificar nada,
        * comprobamos que entre los campos recibidos
        * se encuentrar los obligatorios
        * */
        if (recivedInfo.getNombre()==null) return new ResponseEntity<>("No se ha recibido ningun nombre", HttpStatus.BAD_REQUEST);

        /*
        * Todos los campos recibidos estan OK
        * */
        tokenUser.setNombre(recivedInfo.getNombre());
        tokenUser.setApellidos(recivedInfo.getApellidos());
        tokenUser.setDireccion(recivedInfo.getDireccion());
        tokenUser.setDataNacimiento(recivedInfo.getDataNacimiento());
        tokenUser.setGenero(recivedInfo.getGenero());

        usuarioManager.update(tokenUser);
        return new ResponseEntity<>("Información modificada correctamente", HttpStatus.OK);
    }

    @DeleteMapping("/private/usuario")
    @Transactional
    public ResponseEntity<String> deleteMe(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario tokenUser = tokenManager.getUsuarioFromToken(token);

        usuarioManager.delete(tokenUser);
        return new ResponseEntity<>("Usuariop eliminado correctamente", HttpStatus.OK);
    }

    @PutMapping("/private/usuario/foto")
    @Transactional
    public ResponseEntity<String> saveMyFoto(@RequestPart(value = "file") final MultipartFile uploadfile, HttpServletRequest request) throws IOException {

        /*
         * Cogemos el usuario del token, asi nos aseguramos de
         * que el usuario que se modifica es a si mismo
         * */
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario tokenUser = tokenManager.getUsuarioFromToken(token);

        String imageName = amazonManager.uploadFile(uploadfile);

        if (imageName != null) tokenUser.setFotoPerfil(imageName);

        usuarioManager.update(tokenUser);
        return new ResponseEntity<>("Foto subida correctamente", HttpStatus.OK);
    }

    @GetMapping("/private/usuario/foto")
    @Transactional
    public ResponseEntity<String> getMyFoto(HttpServletRequest request) {

        /*
         * Cogemos el usuario del token, asi nos aseguramos de
         * que el usuario que se modifica es a si mismo
         * */
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario tokenUser = tokenManager.getUsuarioFromToken(token);

        String url=amazonManager.getFile(tokenUser.getFotoPerfil());

        return new ResponseEntity<>(url, HttpStatus.OK);
    }
}
