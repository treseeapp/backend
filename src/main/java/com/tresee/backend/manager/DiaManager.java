package com.tresee.backend.manager;

import com.tresee.backend.enitty.Dia;
import com.tresee.backend.repository.DiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DiaManager {

    @Autowired
    private DiaRepository diaRepository;

    public List<Dia> findAll() {
        List<Dia> toReturn = new LinkedList<>();
        for (Dia dia : diaRepository.findAll()) {
            toReturn.add(dia);
        }
        return toReturn;
    }
}
