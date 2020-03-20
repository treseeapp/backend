package com.tresee.backend.repository;

import com.tresee.backend.enitty.Fichaje;
import org.springframework.data.repository.CrudRepository;

public interface FichajeRepository extends CrudRepository<Fichaje, Long> {
    Fichaje findByIdfichaje(Long id);
}
