package com.tresee.backend.controller;

import com.tresee.backend.enitty.Fichaje;
import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.manager.entity.FichajeManager;
import com.tresee.backend.manager.entity.UsuarioManager;
import com.tresee.backend.manager.seguridad.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class FichajesController {
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private FichajeManager fichajeManager;

    @Autowired
    private UsuarioManager usuarioManager;

    @PostMapping("/private/estudiante/fichaje")
    @Transactional
    public ResponseEntity<String> ficharEntrada(HttpServletRequest request) {

        String ip = request.getHeader("X-FORWARDED-FOR");
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario usuario = tokenManager.getUsuarioFromToken(token);

        if (usuario.getIpFichajes()!=null){
            if (!usuario.getIpFichajes().equals(ip)){
               return new ResponseEntity<>("Has de conectarte a la ip que te han asignado", HttpStatus.BAD_REQUEST);
            }
        }

        List<Fichaje> fichajes = usuario.getFichajes();
        Fichaje ultimoFichaje = null;
        if (fichajes.size() > 0) {
            ultimoFichaje = fichajes.get(fichajes.size() - 1);
        }

        /*
         * Ultimo fichaje tiene la salida fichada ?
         * */
        if (ultimoFichaje == null || ultimoFichaje.getHoraSalida() != null) {
            // SI, fichamos una entrada nueva
            Fichaje fichajeNuevo = new Fichaje();
            fichajeNuevo.setDiaFichaje(LocalDate.now());
            fichajeNuevo.setHoraEntrada(LocalTime.now());
            fichajeNuevo.setUsuario(usuario);
            this.fichajeManager.guardar(fichajeNuevo);
        } else {
            // NO,

            /*
             * La fecha es la misma que hoy ?
             * */
            if (LocalDate.now().equals(ultimoFichaje.getDiaFichaje())) {
                return new ResponseEntity<>("Ya tienes una entrada hoy sin ninguna salida, has de fichar una salida", HttpStatus.BAD_REQUEST);
            } else {
                /*
                 * Ponemos la del dia anterior por defecto a una hora default
                 * */
                String time = "23:59:59";
                ultimoFichaje.setHoraSalida(LocalTime.parse(time));
                this.fichajeManager.guardar(ultimoFichaje);

                // Fichamos una entrada nueva
                Fichaje fichajeNuevo = new Fichaje();
                fichajeNuevo.setDiaFichaje(LocalDate.now());
                fichajeNuevo.setHoraEntrada(LocalTime.now());
                fichajeNuevo.setUsuario(usuario);
                this.fichajeManager.guardar(fichajeNuevo);
            }
        }

        return new ResponseEntity<>("Has fichado correctamente", HttpStatus.OK);
    }

    @PutMapping("/private/estudiante/fichaje")
    @Transactional
    public ResponseEntity<String> ficharSalida(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario usuario = tokenManager.getUsuarioFromToken(token);


        if (usuario.getIpFichajes()!=null){
            if (!usuario.getIpFichajes().equals(ip)){
                return new ResponseEntity<>("Has de conectarte a la ip que te han asignado", HttpStatus.BAD_REQUEST);
            }
        }
        List<Fichaje> fichajes = usuario.getFichajes();
        Fichaje ultimoFichaje = null;
        if (fichajes.size() > 0) {
            ultimoFichaje = fichajes.get(fichajes.size() - 1);
        } else {
            return new ResponseEntity<>("No tienes nignuna entrada para poder fichar salida", HttpStatus.BAD_REQUEST);
        }

        /*
         * Ultimo fichaje tiene la salida fichada ?
         * */
        if (ultimoFichaje.getHoraSalida() == null) {
            /*
             * La fecha es la misma que hoy ?
             * */
            if (LocalDate.now().equals(ultimoFichaje.getDiaFichaje())) {
                //SI, podemos fichar salida
                ultimoFichaje.setHoraSalida(LocalTime.now());
                this.fichajeManager.guardar(ultimoFichaje);
            } else {
                // NO, se ficha con una hora default, y se manda error
                String time = "23:59:59";
                ultimoFichaje.setHoraSalida(LocalTime.parse(time));
                this.fichajeManager.guardar(ultimoFichaje);
                return new ResponseEntity<>("Has de fichar una entrada antes para poder fichar una salida", HttpStatus.BAD_REQUEST);
            }
        } else {
            // SI QUE TIENE SALIDA,
            return new ResponseEntity<>("Has de fichar una entrada antes para poder fichar una salida", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Has fichado correctamente", HttpStatus.OK);
    }
}
