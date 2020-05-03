package com.tresee.backend.manager.entity;

import com.tresee.backend.enitty.Fichaje;
import com.tresee.backend.repository.FichajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichajeManager {
    @Autowired
    private FichajeRepository fichajeRepository;

    public void guardar(Fichaje fichaje) {
        this.fichajeRepository.save(fichaje);
    }
}
