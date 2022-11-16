package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.entity.IstoricPersoana;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.IstoricPersoanaRepository;
import com.usv.siriusvoleiapp.repository.PersoanaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IstoricPersoaneService {
    private final IstoricPersoanaRepository istoricPersoanaRepository;
    private final PersoanaRepository persoanaRepository;

    public IstoricPersoaneService(IstoricPersoanaRepository istoricPersoanaRepository, PersoanaRepository persoanaRepository) {
        this.istoricPersoanaRepository = istoricPersoanaRepository;
        this.persoanaRepository = persoanaRepository;
    }

    public List<IstoricPersoana> getIstoricPersoane(){
        Iterable<IstoricPersoana> iterableIstoricPersoanas=istoricPersoanaRepository.findAll();
        List<IstoricPersoana> istoricPersoana= new ArrayList<>();

        System.out.println(iterableIstoricPersoanas);

        iterableIstoricPersoanas.forEach(istPers->
                istoricPersoana.add(IstoricPersoana.builder()
                                .idIstoricPersoana(istPers.getIdIstoricPersoana())
                                .id(istPers.getId())
                                .post(istPers.getPost())
                                .dataInceput(istPers.getDataInceput())
                                .dataFinal(istPers.getDataFinal())
                        .build()));
        return istoricPersoana;
    }

    public List<IstoricPersoana> getIstoricPersoana(UUID idPers){
        Iterable<IstoricPersoana> iterableIstoricPersoanas=istoricPersoanaRepository.findAll();
        List<IstoricPersoana> istoricPersoana= new ArrayList<>();

        iterableIstoricPersoanas.forEach(istPers->
                istoricPersoana.add(IstoricPersoana.builder()
                        .idIstoricPersoana(istPers.getIdIstoricPersoana())
                        .id(istPers.getId())
                        .post(istPers.getPost())
                        .dataInceput(istPers.getDataInceput())
                        .dataFinal(istPers.getDataFinal())
                        .build()));
        return istoricPersoana.stream().filter(istPers->istPers.getId().equals(idPers)).collect(Collectors.toList());
    }


}
