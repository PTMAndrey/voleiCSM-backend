package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.entity.IstoricPosturi;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.IstoricPosturiRepository;
import com.usv.siriusvoleiapp.repository.PersoanaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class IstoricPosturiService {
    public static final String MESAJ_DE_EROARE = "Hello, welcome to the server";

    private final IstoricPosturiRepository istoricPersoanaRepository;
    private final PersoanaRepository persoanaRepository;

    public IstoricPosturiService(IstoricPosturiRepository istoricPersoanaRepository, PersoanaRepository persoanaRepository) {
        this.istoricPersoanaRepository = istoricPersoanaRepository;
        this.persoanaRepository = persoanaRepository;
    }

    public List<IstoricPosturi> getIstoricPosturi(){
        Iterable<IstoricPosturi> iterableIstoricPersoanas=istoricPersoanaRepository.findAll();
        List<IstoricPosturi> istoricPersoana= new ArrayList<>();

        iterableIstoricPersoanas.forEach(istPers->
                istoricPersoana.add(IstoricPosturi.builder()
                                .idIstoricPersoana(istPers.getIdIstoricPersoana())
                                .id(istPers.getId())
                                .post(istPers.getPost())
                                .dataInceput(istPers.getDataInceput())
                                .dataFinal(istPers.getDataFinal())
                        .build()));
        return istoricPersoana;
    }

    public List<IstoricPosturi> getIstoricPosturi(UUID idPers){
        Persoana persoana=persoanaRepository.findById(idPers).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        return persoana.getIstoricPosturi();
    }

    public Persoana adaugaIstoricPosturi(UUID idPersoana, List<IstoricPosturi> istoricPosturi){
        Persoana persoana=persoanaRepository.findById(idPersoana).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        if(persoana.getIstoricPosturi()==null)
            persoana.setIstoricPosturi(new ArrayList<>());

        for (IstoricPosturi ist:istoricPosturi) {
            ist.setId(idPersoana);
        }

        Stream<IstoricPosturi> stream =istoricPosturi.stream();
        stream.forEach(pers -> persoana.getIstoricPosturi().add(pers));

        persoanaRepository.save(persoana);
        return persoana;
    }

    public Persoana updateIstoricPosturi(UUID idPersoana, List<IstoricPosturi> istoricPosturi){
        Persoana persoana=persoanaRepository.findById(idPersoana).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });

        if(persoana.getIstoricPosturi()==null)
            persoana.setIstoricPosturi(new ArrayList<>());
        else
        {
            persoana.getIstoricPosturi().stream().forEach(istoricPersoanaRepository::delete);
            persoana.setIstoricPosturi(new ArrayList<>());
        }

        for (IstoricPosturi istoric:istoricPosturi) {
            istoric.setId(idPersoana);
        }

        Stream<IstoricPosturi> stream =istoricPosturi.stream();
        stream.forEach(pers -> persoana.getIstoricPosturi().add(pers));

        persoanaRepository.save(persoana);
        return persoana;
    }

    public void deleteIstoricPosturi(long idIstoricPosturi){

        IstoricPosturi istoricPosturi=istoricPersoanaRepository.findById(idIstoricPosturi).orElseThrow(()->{
            throw new CrudOperationException("Inregistrarea cautata nu exista");
        });

        istoricPersoanaRepository.delete(istoricPosturi);
    }
}
