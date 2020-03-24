package com.tresee.backend.manager;

import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UsuarioManager {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        List<Usuario> toReturn = new LinkedList<>();
        for (Usuario user : usuarioRepository.findAll()) {
            toReturn.add(user);
        }
        return toReturn;
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findByIdusuario(id);
    }

    public Usuario findByEmail(Long id) {
        return usuarioRepository.findByIdusuario(id);
    }

    public void update(Usuario user) {
        usuarioRepository.save(user);
    }

    public void delete(Usuario user) {
        usuarioRepository.delete(user);
    }
}
