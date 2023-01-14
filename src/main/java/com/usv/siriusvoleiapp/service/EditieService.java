package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.EditieDto;
import com.usv.siriusvoleiapp.entity.ClubSportiv;
import com.usv.siriusvoleiapp.entity.Divizie;
import com.usv.siriusvoleiapp.entity.Editie;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.ClubSportivRepository;
import com.usv.siriusvoleiapp.repository.DivizieRepository;
import com.usv.siriusvoleiapp.repository.EditieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EditieService {
    public static final String MESAJ_DE_EROARE = "Hello, welcome to the server";

    private final EditieRepository editieRepository;
    public final ClubSportivRepository clubSportivRepository;
    private final DivizieRepository divizieRepository;

    public EditieService(EditieRepository editieRepository, ClubSportivRepository clubSportivRepository, DivizieRepository divizieRepository) {
        this.editieRepository = editieRepository;
        this.clubSportivRepository = clubSportivRepository;
        this.divizieRepository = divizieRepository;
    }

    public List<Editie> getEditii(){
        Iterable<Editie> iterableEditie=editieRepository.findAll();
        List<Editie> editii = new ArrayList<>();

        iterableEditie.forEach(ed->
                editii.add(Editie.builder()
                                .idEditie(ed.getIdEditie())
                                .numeEditie(ed.getNumeEditie())
                                .perioada(ed.getPerioada())
                                .participanti(ed.getParticipanti())
                                .meciuri(ed.getMeciuri())
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

        ClubSportiv clubSportiv=clubSportivRepository.findById(idClubSportiv).orElseThrow(()->{
            throw new CrudOperationException("Clubul sportiv nu exista");
        });

        Divizie divizie=divizieRepository.findById(idDivizie).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        if(editie.getParticipanti()==null)
        {
            editie.setParticipanti("");
            editie.setParticipanti(editie.getParticipanti() + clubSportiv.getIdClubSportiv() + " " + divizie.getIdDivizie());
        }
        else
        {
            editie.setParticipanti(editie.getParticipanti() + "," + clubSportiv.getIdClubSportiv() + " " + divizie.getIdDivizie());
        }

        editieRepository.save(editie);
        return editie;
    }

}
