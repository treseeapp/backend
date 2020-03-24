package com.tresee.backend.repository;

import com.tresee.backend.enitty.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Usuario findByIdusuario(Long id);

    Usuario findByEmail(String email);
}
