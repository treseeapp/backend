package com.tresee.backend.controller;

import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.enitty.enums.ModoInicioSesion;
import com.tresee.backend.enitty.enums.Rol;
import com.tresee.backend.enitty.modelCsv.UsuarioCSV;
import com.tresee.backend.enitty.modelNotMapped.UsuarioConEmpresa;
import com.tresee.backend.manager.AmazonManager;
import com.tresee.backend.manager.CsvManager;
import com.tresee.backend.manager.TokenManager;
import com.tresee.backend.manager.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioManager usuarioManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private AmazonManager amazonManager;

    @Autowired
    private CsvManager csvManager;

    @GetMapping("/private/usuario")
    public Usuario getMyInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario user = tokenManager.getUsuarioFromToken(token);

        user.setFotoPerfil(this.amazonManager.getFile(user.getFotoPerfil()));
        return user;
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
        if (recivedInfo.getNombre() == null)
            return new ResponseEntity<>("No se ha recibido ningun nombre", HttpStatus.BAD_REQUEST);

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
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
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

        if (imageName != null) {
            if (tokenUser.getFotoPerfil() != null && !tokenUser.getFotoPerfil().equals(""))
                this.amazonManager.deletePicture(tokenUser.getFotoPerfil());
            tokenUser.setFotoPerfil(imageName);
        }

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

        if (tokenUser.getFotoPerfil() == null || tokenUser.getFotoPerfil().equals("")) {
            return new ResponseEntity<>("No tienes foto perfil", HttpStatus.BAD_REQUEST);
        }
        String url = amazonManager.getFile(tokenUser.getFotoPerfil());

        return new ResponseEntity<>(url, HttpStatus.OK);
    }


    /*
     * ---------------
     *
     *  Administrados
     *
     * ---------------
     * */
    @GetMapping("/admin/estudiantes")
    public List<UsuarioConEmpresa> getAllStudents() {
        List<Usuario> allUsers = this.usuarioManager.findAll();

        Stream<Usuario> stream = allUsers.stream().filter(usuario -> usuario.getRol() == Rol.ESTUDIANTE);
        List<Usuario> users = stream.collect(Collectors.toList());
        List<UsuarioConEmpresa> toReturn = new LinkedList<>();

        users.forEach(usuario -> {
            usuario.setFotoPerfil(this.amazonManager.getFile(usuario.getFotoPerfil()));

            UsuarioConEmpresa individual = new UsuarioConEmpresa();
            Empresa empresaIndividual = new Empresa();

            individual.setIdusuario(usuario.getIdusuario());
            individual.setNombre(usuario.getNombre());
            individual.setApellidos(usuario.getApellidos());
            individual.setEmail(usuario.getEmail());
            individual.setDataNacimiento(usuario.getDataNacimiento());
            individual.setGenero(usuario.getGenero());

            if (usuario.getEmpresa() != null) {
                empresaIndividual.setIdempresa(usuario.getEmpresa().getIdempresa());
                empresaIndividual.setNombre(usuario.getEmpresa().getNombre());
                individual.setEmpresa(empresaIndividual);
            } else {
                individual.setEmpresa(null);
            }
            individual.setFichajes(usuario.getFichajes());
            individual.setFotoPerfil(usuario.getFotoPerfil());
            individual.setDireccion(usuario.getDireccion());

            toReturn.add(individual);
        });
        return toReturn;
    }

    @GetMapping("/admin/estudiantes/{id}")
    public Usuario getSpecificStudent(@PathVariable Long id, HttpServletResponse response) throws IOException {

        Usuario user = this.usuarioManager.findById(id);
        if (user.getRol() != Rol.ESTUDIANTE) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Este usuario no es un estudiante");
            return null;
        }
        user.setFotoPerfil(this.amazonManager.getFile(user.getFotoPerfil()));
        return user;
    }

    @GetMapping("/admin/estudiantes/{id}/foto")
    @Transactional
    public ResponseEntity<String> getStudentFoto(@PathVariable Long id) {

        Usuario usuario = usuarioManager.findById(id);

        if (usuario.getFotoPerfil() == null || usuario.getFotoPerfil().equals("")) {
            return new ResponseEntity<>("El usuario " + usuario.getNombre() + " no tiene foto de perfil", HttpStatus.BAD_REQUEST);
        }
        String url = amazonManager.getFile(usuario.getFotoPerfil());
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping("/admin/usuario/convert/profesor")
    @Transactional
    public ResponseEntity<String> modifyToTeacher(@RequestBody String json) {
        Usuario user = this.usuarioManager.fromJson(json);

        if (user.getIdusuario() == null)
            return new ResponseEntity<>("Necesitamos recibir el id del usuario que quieres convertir", HttpStatus.BAD_REQUEST);

        Usuario userToModify = this.usuarioManager.findById(user.getIdusuario());

        if (userToModify == null)
            return new ResponseEntity<>("Usuario con id(" + user.getIdusuario() + ") no existente", HttpStatus.BAD_REQUEST);
        if (userToModify.getRol() != Rol.ESTUDIANTE)
            return new ResponseEntity<>("El usuario que quieres convertir a profesor ha de ser un estudiante", HttpStatus.BAD_REQUEST);

        userToModify.setRol(Rol.PROFESOR);
        this.usuarioManager.update(userToModify);
        return new ResponseEntity<>("Usuario convertido a profesor", HttpStatus.OK);
    }


    @PostMapping("/admin/estudiantes/upload/csv")
    public ResponseEntity<String> addStudentCsv(@RequestPart(value = "file") final MultipartFile csv) {

        if (csv.isEmpty()) {
            return new ResponseEntity<>("Archivo csv no recivido", HttpStatus.BAD_REQUEST);
        }

        try {
            List<UsuarioCSV> usuariosCsv = this.csvManager.toUsuario(csv);
            for (UsuarioCSV user : usuariosCsv) {
                if (this.usuarioManager.findByEmail(user.getEmail()) != null) continue;

                Usuario usuario = new Usuario();
                usuario.setNombre(user.getNombre());
                usuario.setApellidos(user.getApellidos());
                usuario.setEmail(user.getEmail());
                usuario.setModoInicioSesion(ModoInicioSesion.LOCAL);
                usuario.setRol(Rol.ESTUDIANTE);

                this.usuarioManager.update(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ha habido un error", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Usuarios añadidos correctamente", HttpStatus.OK);
    }

}
