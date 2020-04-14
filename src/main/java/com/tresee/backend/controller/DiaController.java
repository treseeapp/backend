package com.tresee.backend.controller;

import com.tresee.backend.enitty.Dia;
import com.tresee.backend.manager.DiaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiaController {
    @Autowired
    private DiaManager diaManager;

    @GetMapping("/admin/dias")
    @Transactional
    public List<Dia> getAllDias() {
        return diaManager.findAll();
    }
}
