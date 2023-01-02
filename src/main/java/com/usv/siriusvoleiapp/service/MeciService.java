package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.MeciDto;
import com.usv.siriusvoleiapp.entity.ClubSportiv;
import com.usv.siriusvoleiapp.entity.Meci;
import com.usv.siriusvoleiapp.repository.MeciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeciService {
    @Autowired
    private AzureBlobService azureBlobAdapter;

    private final MeciRepository meciRepository;
    public final ClubSportivService clubSportivService;

    public MeciService(MeciRepository meciRepository, ClubSportivService clubSportivService) {
        this.meciRepository = meciRepository;
        this.clubSportivService = clubSportivService;
    }

    //add, update scor, update general?, delete
    public List<Meci> getMeniuri(){
        Iterable<Meci> iterableMeciuri=meciRepository.findAll();
        List<Meci> meciuri= new ArrayList<>();

        for(Meci meci: iterableMeciuri){
            ClubSportiv clubSportiv=clubSportivService.getCluburiSportiveDupaNume(meci.getNumeAdversar());

            meciuri.add(Meci.builder()
                    .id(meci.getId())
                    .idEditie(meci.getIdEditie())
                    .status(meci.getStatus())
                    .data(meci.getData())
                    .numeAdversar(meci.getNumeAdversar())
                    .logoAdversar(clubSportiv.getLogo().length()!=0?clubSportiv.getLogo():"")
                    .locatie(meci.getLocatie())
                    .scorCSM(meci.getScorCSM())
                    .scorAdversar(meci.getScorAdversar())
                    .teren(meci.getTeren())
                    .build());
        }

        return meciuri;
    }

    public Meci addMeci(MeciDto meciDto){
        ClubSportiv clubSportiv=clubSportivService.getCluburiSportiveDupaNume(meciDto.getNumeAdversar());

        Meci meci=Meci.builder()
                .idEditie(meciDto.getIdEditie())
                .status(meciDto.getStatus())
                .data(meciDto.getData())
                .numeAdversar(meciDto.getNumeAdversar())
                .logoAdversar(clubSportiv.getLogo().length()!=0? clubSportiv.getLogo() : "")
                .locatie(meciDto.getLocatie())
                .scorCSM(meciDto.getScorCSM())
                .scorAdversar(meciDto.getScorAdversar())
                .teren(meciDto.getTeren())
                .build();

        meciRepository.save(meci);
        return meci;
    }

}
