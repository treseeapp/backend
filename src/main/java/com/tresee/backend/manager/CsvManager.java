package com.tresee.backend.manager;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.tresee.backend.enitty.modelCsv.UsuarioCSV;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

@Service
public class CsvManager {

    public List<UsuarioCSV> toUsuario(MultipartFile csv) throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(csv.getInputStream()));
        CsvToBean<UsuarioCSV> csvToBean = new CsvToBeanBuilder(reader)
                .withType(UsuarioCSV.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List<UsuarioCSV> toReturn = new LinkedList<>();
        for (UsuarioCSV user : (Iterable<UsuarioCSV>) csvToBean) {
            if (user.getEmail() == null || user.getNombre() == null || user.getApellidos() == null) continue;

            toReturn.add(user);
        }
        return toReturn;
    }
}
