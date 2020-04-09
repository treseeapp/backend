package com.tresee.backend.repository;

import com.tresee.backend.enitty.Dia;
import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.enitty.EmpresaTieneDia;
import org.springframework.data.repository.CrudRepository;

public interface EmpresaTieneDiaRepository extends CrudRepository<EmpresaTieneDia, Long> {
    EmpresaTieneDia findByDiaAndEmpresa(Dia dia, Empresa empresa);
}
