package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.entity.RealizariPersonale;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.PersoanaRepository;
import com.usv.siriusvoleiapp.repository.RealizariPersonaleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RealizariPersonaleService {
    private final RealizariPersonaleRepository realizariPersonaleRepository;
    private final PersoanaRepository persoanaRepository;

    public RealizariPersonaleService(RealizariPersonaleRepository realizariPersonaleRepository, PersoanaRepository persoanaRepository) {
        this.realizariPersonaleRepository = realizariPersonaleRepository;
        this.persoanaRepository = persoanaRepository;
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

    public List<RealizariPersonale> getRealizariPersoana(UUID idPers){
        Persoana persoana=persoanaRepository.findById(idPers).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });

        return persoana.getRealizariPersonale();
    }

    public Persoana adaugaRealizariPersonale(UUID idPersoana, List<RealizariPersonale> realizariPersonale){
        Persoana persoana=persoanaRepository.findById(idPersoana).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });

        if(persoana.getRealizariPersonale()==null)
            persoana.setRealizariPersonale(new ArrayList<>());

        for (RealizariPersonale realizari:realizariPersonale) {
            realizari.setId(idPersoana);
        }

        realizariPersonale.stream().map(pers->
                        persoana.getRealizariPersonale().add(pers))
                .collect(Collectors.toSet());

        persoanaRepository.save(persoana);
        return persoana;
    }

    public Persoana updateRealizariPersonale(UUID idPersoana, List<RealizariPersonale> realizariPersonale){
        Persoana persoana=persoanaRepository.findById(idPersoana).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });

        if(persoana.getRealizariPersonale()==null)
            persoana.setRealizariPersonale(new ArrayList<>());
        else
        {
            persoana.getRealizariPersonale().stream().forEach(realizare->
                    realizariPersonaleRepository.delete(realizare));
            persoana.setRealizariPersonale(new ArrayList<>());
        }

        for (RealizariPersonale realizari:realizariPersonale) {
            realizari.setId(idPersoana);
        }

        realizariPersonale.stream().map(pers->
                        persoana.getRealizariPersonale().add(pers))
                .collect(Collectors.toSet());

        persoanaRepository.save(persoana);
        return persoana;
    }

    public void deleteRealizarePersonala(long idRealizariPersonale){
//        Persoana persoana=persoanaRepository.findById(idPersoana).orElseThrow(()->{
//            throw new CrudOperationException("Persoana nu exista");
//        });

        RealizariPersonale realizarePersonala=realizariPersonaleRepository.findById(idRealizariPersonale).orElseThrow(()->{
            throw new CrudOperationException("Inregistrarea cautata nu exista");
        });

        realizariPersonaleRepository.delete(realizarePersonala);
    }




}
