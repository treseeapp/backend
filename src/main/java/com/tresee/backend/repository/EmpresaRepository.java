package com.tresee.backend.repository;

import com.tresee.backend.enitty.Empresa;
import org.springframework.data.repository.CrudRepository;

public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
    Empresa findByIdempresa(Long id);
}
