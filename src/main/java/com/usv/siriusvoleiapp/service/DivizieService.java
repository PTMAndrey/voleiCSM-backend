package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.ClubSportivDto;
import com.usv.siriusvoleiapp.dto.DivizieDto;
import com.usv.siriusvoleiapp.entity.ClubSportiv;
import com.usv.siriusvoleiapp.entity.Divizie;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.DivizieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DivizieService {
    public static final String MESAJ_DE_EROARE = "Hello, welcome to the server";

    @Autowired
    private AzureBlobService azureBlobAdapter;

    private final DivizieRepository divizieRepository;

    public DivizieService(DivizieRepository divizieRepository) {
        this.divizieRepository = divizieRepository;
    }

    public List<DivizieDto> getDivizii(){
        Iterable<Divizie> iterableDivizii=divizieRepository.findAll();
        List<DivizieDto> divizii=new ArrayList<>();

        iterableDivizii.forEach(divizie->

                divizii.add(DivizieDto.builder()
                                .idDivizie(divizie.getIdDivizie())
                                .denumireDivizie(divizie.getDenumireDivizie())
                                .build()));
        return divizii;
    }

    public DivizieDto addDivizie(DivizieDto divizieDto){
        Divizie divizie = Divizie.builder()
                .idDivizie(divizieDto.getIdDivizie())
                .denumireDivizie(divizieDto.getDenumireDivizie())
                .build();
        divizieRepository.save(divizie);
        return divizieDto;
    }

    public DivizieDto updateDivizie(Long id, DivizieDto divizieDto){
        Divizie divizie=divizieRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        divizie.setIdDivizie(divizieDto.getIdDivizie());
        divizie.setDenumireDivizie(divizieDto.getDenumireDivizie());

        divizieRepository.save(divizie);
        return divizieDto;
    }

    public void deleteDivizie(Long id){
        Divizie divizie=divizieRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        divizieRepository.delete(divizie);
    }

    public List<ClubSportivDto> getCluburiSportiveDinDivizie(Long idDivizie){
        Divizie divizie=divizieRepository.findById(idDivizie).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        List<ClubSportiv> cluburiSportive = divizie.getCluburiSportive();

        return cluburiSportive.stream()
                .map(club->ClubSportivDto.builder()
                        .numeClubSportiv(club.getNumeClubSportiv())
                        .logo(azureBlobAdapter.getFileURL(club.getLogo()))
                        .build()).toList();
    }
}
