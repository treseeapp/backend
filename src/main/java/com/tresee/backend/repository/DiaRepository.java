package com.tresee.backend.repository;

import com.tresee.backend.enitty.Dia;
import org.springframework.data.repository.CrudRepository;

public interface DiaRepository extends CrudRepository<Dia, Long> {
    Dia findByIddia(Long id);
}
