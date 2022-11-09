package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.entity.IstoricPersoana;
import com.usv.siriusvoleiapp.repository.IstoricPersoanaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IstoricPersoaneService {
    private final IstoricPersoanaRepository istoricPersoanaRepository;

    public IstoricPersoaneService(IstoricPersoanaRepository istoricPersoanaRepository) {
        this.istoricPersoanaRepository = istoricPersoanaRepository;
    }

    public List<IstoricPersoana> getIstoricPersoane(){
        Iterable<IstoricPersoana> iterableIstoricPersoanas=istoricPersoanaRepository.findAll();
        List<IstoricPersoana> istoricPersoana= new ArrayList<>();

        iterableIstoricPersoanas.forEach(istPers->
                istoricPersoana.add(IstoricPersoana.builder()
                                .idPersoana(istPers.getIdPersoana())
                                .post(istPers.getPost())
                                .dataInceput(istPers.getDataInceput())
                                .dataFinal(istPers.getDataFinal())
                        .build()));
        return istoricPersoana;
    }
}
