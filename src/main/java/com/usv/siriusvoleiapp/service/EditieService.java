package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.EditieDto;
import com.usv.siriusvoleiapp.entity.Editie;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.EditieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EditieService {
    private final EditieRepository editieRepository;

    public EditieService(EditieRepository editieRepository) {
        this.editieRepository = editieRepository;
    }

    public List<Editie> getEditii(){
        Iterable<Editie> iterableEditie=editieRepository.findAll();
        List<Editie> editii = new ArrayList<>();

        iterableEditie.forEach(ed->
                editii.add(Editie.builder()
                                .idEditie(ed.getIdEditie())
                                .numeEditie(ed.getNumeEditie())
                                .perioada(ed.getPerioada())
                                .idCluburiSportive(ed.getIdCluburiSportive())
                                .idDivizii(ed.getIdDivizii())
                        .build()));
        return editii;
    }

    public Editie addEditie(EditieDto editieDto){
        Editie editie=Editie.builder()
                .numeEditie(editieDto.getNumeEditie())
                .perioada(editieDto.getPerioada())
                .build();
        editieRepository.save(editie);
        return editie;
    }

    public Editie ubdateEditie(){
        return new Editie();
    }

    public void deleteEditie(Long id){
        Editie editie=editieRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Editia nu exista");
        });

        editieRepository.delete(editie);
    }

    public Editie addParticipantLaEditie(Long idEditie, Long idClubSportiv, Long idDivizie){
        Editie editie=editieRepository.findById(idEditie).orElseThrow(()->{
            throw new CrudOperationException("Editia nu exista");
        });

        if(editie.getIdCluburiSportive()==null||editie.getIdDivizii()==null)
        {
            editie.setIdCluburiSportive(new ArrayList<>());
            editie.setIdDivizii(new ArrayList<>());
        }
        else{
            editie.getIdCluburiSportive().add(idClubSportiv);
            editie.getIdDivizii().add(idDivizie);
        }

        editieRepository.save(editie);
        return editie;
    }


}
