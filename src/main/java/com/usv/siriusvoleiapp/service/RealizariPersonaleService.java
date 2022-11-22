package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.entity.IstoricPersoana;
import com.usv.siriusvoleiapp.entity.RealizariPersonale;
import com.usv.siriusvoleiapp.repository.RealizariPersonaleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RealizariPersonaleService {
    private final RealizariPersonaleRepository realizariPersonaleRepository;

    public RealizariPersonaleService(RealizariPersonaleRepository realizariPersonaleRepository) {
        this.realizariPersonaleRepository = realizariPersonaleRepository;
    }

    public List<RealizariPersonale> getRealizariPersonale(){
        Iterable<RealizariPersonale> iterableRealizariPersonales=realizariPersonaleRepository.findAll();
        List<RealizariPersonale> realizariPersonale= new ArrayList<>();

        iterableRealizariPersonales.forEach(istPers->
                realizariPersonale.add(RealizariPersonale.builder()
                                .idRealizariPersonale(istPers.getIdRealizariPersonale())
                                .id(istPers.getId())
                                .denumireRezultat(istPers.getDenumireRezultat())
                                .dataObtinerii(istPers.getDataObtinerii())
                        .build()));
        return realizariPersonale;
    }
}
