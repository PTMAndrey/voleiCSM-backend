package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.ClubSportivDto;
import com.usv.siriusvoleiapp.entity.ClubSportiv;
import com.usv.siriusvoleiapp.entity.Divizie;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.ClubSportivRepository;
import com.usv.siriusvoleiapp.repository.DivizieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClubSportivService {
    public final ClubSportivRepository clubSportivRepository;
    public final DivizieRepository divizieRepository;

    public ClubSportivService(ClubSportivRepository clubSportivRepository, DivizieRepository divizieRepository) {
        this.clubSportivRepository = clubSportivRepository;
        this.divizieRepository = divizieRepository;
    }

    public List<ClubSportivDto> getCluburiSportive(){
        Iterable<ClubSportiv> iterableCluburiSportive = clubSportivRepository.findAll();
        List<ClubSportivDto> cluburiSportive= new ArrayList<>();

        iterableCluburiSportive.forEach(clubSportiv ->
                cluburiSportive.add(ClubSportivDto.builder()
                                .numeClubSportiv(clubSportiv.getNumeClubSportiv())
                                .viziuneClubSportiv(clubSportiv.getViziuneClubSportiv())
                                .istorieClubSportiv(clubSportiv.getIstorieClubSportiv())
                                .build()));
        return cluburiSportive;
    }

    public ClubSportivDto addClubSportiv(ClubSportivDto clubSportivDto){
        ClubSportiv clubSportiv=ClubSportiv.builder()
                .numeClubSportiv(clubSportivDto.getNumeClubSportiv())
                .viziuneClubSportiv(clubSportivDto.getViziuneClubSportiv())
                .istorieClubSportiv(clubSportivDto.getIstorieClubSportiv())
                .build();
        clubSportivRepository.save(clubSportiv);
        return clubSportivDto;
    }

    public ClubSportivDto updateClubSportiv(Long id, ClubSportivDto clubSportivDto){
        ClubSportiv clubSportiv=clubSportivRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Clubul sportiv nu exista");
        });

        clubSportiv.setNumeClubSportiv(clubSportivDto.getNumeClubSportiv());
        clubSportiv.setIstorieClubSportiv(clubSportivDto.getIstorieClubSportiv());
        clubSportiv.setViziuneClubSportiv(clubSportivDto.getViziuneClubSportiv());

        clubSportivRepository.save(clubSportiv);
        return clubSportivDto;
    }

    public void deleteClubSportiv(Long id){
        ClubSportiv clubSportiv=clubSportivRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Clubul sportiv nu exista");
        });

        clubSportivRepository.delete(clubSportiv);
    }

    public ClubSportiv adaugareDivizieLaClubSportiv(Long idClubSportiv, Long idDivizie){
        ClubSportiv clubSportiv=clubSportivRepository.findById(idClubSportiv).orElseThrow(()->{
            throw new CrudOperationException("Clubul sportiv nu exista");
        });

        Divizie divizie=divizieRepository.findById(idDivizie).orElseThrow(()->{
            throw new CrudOperationException("Divizia nu exista");
        });

        if(clubSportiv.getDivizii()==null)
            clubSportiv.setDivizii(new ArrayList<>());

        clubSportiv.getDivizii().add(divizie);
        clubSportivRepository.save(clubSportiv);

        return clubSportiv;
    }
}
