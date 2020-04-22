package com.tresee.backend.controller;

import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.enitty.EmpresaTieneDia;
import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.enitty.enums.Rol;
import com.tresee.backend.manager.AmazonManager;
import com.tresee.backend.manager.EmpresaManager;
import com.tresee.backend.manager.TokenManager;
import com.tresee.backend.manager.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
public class EmpresaController {

    @Autowired
    private EmpresaManager empresaManager;

    @Autowired
    private UsuarioManager usuarioManager;

    @Autowired
    private AmazonManager amazonManager;

    @Autowired
    private TokenManager tokenManager;

    @GetMapping("/admin/empresas")
    public List<Empresa> getEmpresas() {
        for (Empresa company : this.empresaManager.getAll()) {
            company.setFotoEmpresa(this.amazonManager.getFile(company.getFotoEmpresa()));
        }
        return this.empresaManager.getAll();
    }

    @GetMapping("/private/my/empresa")
    public Empresa getMyEmpresa(HttpServletRequest request) {
        Empresa toReturn = new Empresa();
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario tokenUser = tokenManager.getUsuarioFromToken(token);

        Empresa empresaOriginal = tokenUser.getEmpresa();

        if (empresaOriginal == null) {
            return toReturn;
        }

        toReturn.setNombre(empresaOriginal.getNombre());
        toReturn.setContacto(empresaOriginal.getContacto());
        toReturn.setDireccion(empresaOriginal.getDireccion());


        Set<Usuario> estudiantes = new HashSet<>();
        for (Usuario user : empresaOriginal.getEstudiantes()) {
            Usuario toAdd = new Usuario();
            toAdd.setNombre(user.getNombre());
            toAdd.setApellidos(user.getApellidos());
            estudiantes.add(toAdd);
        }
        toReturn.setEstudiantes(estudiantes);


        List<EmpresaTieneDia> horarios = new LinkedList<>();
        for (EmpresaTieneDia horario : empresaOriginal.getEmpresaTieneDias()) {
            EmpresaTieneDia toAdd = new EmpresaTieneDia();
            toAdd.setDia(horario.getDia());
            toAdd.setHoraEntrada(horario.getHoraEntrada());
            toAdd.setHoraSalida(horario.getHoraSalida());

            horarios.add(toAdd);
        }
        toReturn.setEmpresaTieneDias(horarios);

        toReturn.setFotoEmpresa(amazonManager.getFile(empresaOriginal.getFotoEmpresa()));
        return toReturn;
    }

    @GetMapping("/admin/empresas/{id}")
    public Empresa getEmpresas(@PathVariable Long id) {
        Empresa company = this.empresaManager.findById(id);
        company.setFotoEmpresa(this.amazonManager.getFile(company.getFotoEmpresa()));
        return company;
    }

    @PostMapping("/admin/empresas")
    @Transactional
    public ResponseEntity<String> addEmpresa(@RequestBody String json) {
        Empresa empresa = empresaManager.fromJsonCreate(json);

        /*
         * Comprobamos que recibimos los parametros obligatorios para crear una empresa
         * */
        if (empresa.getNombre() == null
                || empresa.getContacto() == null)
            return new ResponseEntity<>("No se han recibido los parametros obligatorios", HttpStatus.BAD_REQUEST);

        empresaManager.create(empresa);
        return new ResponseEntity<>("Empresa creada correctamente", HttpStatus.OK);
    }

    @PutMapping("/admin/empresas")
    @Transactional
    public ResponseEntity<String> modifyEmpresa(@RequestBody String json) {
        Empresa empresa = empresaManager.fromJsonCreate(json);
        if (empresa.getIdempresa() == null)
            return new ResponseEntity<>("No se ha recibido ninung id para modificar", HttpStatus.BAD_REQUEST);
        if (empresa.getNombre() == null
                || empresa.getContacto() == null)
            return new ResponseEntity<>("No se han recibido los parametros obligatorios", HttpStatus.BAD_REQUEST);

        Empresa empresaToModify = this.empresaManager.findById(empresa.getIdempresa());
        empresaToModify.setNombre(empresa.getNombre());
        empresaToModify.setContacto(empresa.getContacto());
        empresaToModify.setDireccion(empresa.getDireccion());
        empresaToModify.setInicioPracticas(empresa.getInicioPracticas());

        empresaManager.update(empresaToModify);
        return new ResponseEntity<>("Empresa modificada correctamente", HttpStatus.OK);
    }

    @DeleteMapping("/admin/empresas")
    @Transactional
    public ResponseEntity<String> deleteEmpresa(@RequestBody String json) {

        Empresa empresa = empresaManager.fromJsonCreate(json);
        if (empresa.getIdempresa() == null)
            return new ResponseEntity<>("No se ha recibido ninung ID para eliminar", HttpStatus.BAD_REQUEST);
        Empresa empresaABorrar = this.empresaManager.findById(empresa.getIdempresa());
        this.empresaManager.delete(empresaABorrar);

        return new ResponseEntity<>("Empresa eliminada correctamente", HttpStatus.OK);
    }

    @PutMapping("/admin/empresas/{id}/foto")
    @Transactional
    public ResponseEntity<String> saveCompanyFoto(@RequestPart(value = "file") final MultipartFile uploadfile, @PathVariable Long id) {

        String imageName = amazonManager.uploadFile(uploadfile);
        Empresa empresa = empresaManager.findById(id);

        if (imageName != null) {

            if (empresa.getFotoEmpresa() != null && !empresa.getFotoEmpresa().equals(""))
                this.amazonManager.deletePicture(empresa.getFotoEmpresa());
            empresa.setFotoEmpresa(imageName);
        }

        empresaManager.update(empresa);
        return new ResponseEntity<>("Foto subida correctamente.", HttpStatus.OK);
    }

    @PostMapping("/admin/empresas/vincular/user")
    @Transactional
    public ResponseEntity<String> vinculateUser(@RequestBody String json) {
        Empresa empresa = this.empresaManager.fromJsonCreate(json);
        Usuario usuario = this.usuarioManager.fromJson(json);
        empresa = this.empresaManager.findById(empresa.getIdempresa());
        usuario = this.usuarioManager.findById(usuario.getIdusuario());

        if (empresa == null) return new ResponseEntity<>("Empresa no existente", HttpStatus.BAD_REQUEST);
        if (usuario == null) return new ResponseEntity<>("Usuario no existente", HttpStatus.BAD_REQUEST);
        if (usuario.getRol() != Rol.ESTUDIANTE)
            return new ResponseEntity<>("Solo se puede vincular a un estudiante con una empresa", HttpStatus.BAD_REQUEST);

        usuario.setEmpresa(empresa);
        this.usuarioManager.update(usuario);

        return new ResponseEntity<>("Usuario vinculado correctamente", HttpStatus.OK);
    }

    @DeleteMapping("/admin/empresas/vincular/user")
    @Transactional
    public ResponseEntity<String> desvincularUsuario(@RequestBody String json) {
        Usuario usuario = this.usuarioManager.fromJson(json);
        usuario = this.usuarioManager.findById(usuario.getIdusuario());

        if (usuario == null) return new ResponseEntity<>("Usuario no existe", HttpStatus.BAD_REQUEST);
        if (usuario.getRol() != Rol.ESTUDIANTE)
            return new ResponseEntity<>("El usuario no es un estudiante, con lo cual no esta vinculado a ninguna empresa", HttpStatus.BAD_REQUEST);
        if (usuario.getEmpresa() == null)
            return new ResponseEntity<>("El usuario no tiene empresa", HttpStatus.BAD_REQUEST);

        usuario.setEmpresa(null);
        this.usuarioManager.update(usuario);

        return new ResponseEntity<>("Usuario desvinculado correctamente", HttpStatus.OK);
    }
}
