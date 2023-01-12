package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusMeci;
import com.usv.siriusvoleiapp.dto.MeciDto;
import com.usv.siriusvoleiapp.entity.ClubSportiv;
import com.usv.siriusvoleiapp.entity.Editie;
import com.usv.siriusvoleiapp.entity.Meci;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.EditieRepository;
import com.usv.siriusvoleiapp.repository.MeciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeciService {
    public static final String MESAJ_DE_EROARE = "Meciul nu exista";

    @Autowired
    private AzureBlobService azureBlobAdapter;

    private final MeciRepository meciRepository;
    public final ClubSportivService clubSportivService;
    public final EditieRepository editieRepository;

    public MeciService(MeciRepository meciRepository, ClubSportivService clubSportivService, EditieRepository editieRepository) {
        this.meciRepository = meciRepository;
        this.clubSportivService = clubSportivService;
        this.editieRepository = editieRepository;
    }

    //add, update scor, update general?, delete
    public List<Meci> getMeciuri(){
        Iterable<Meci> iterableMeciuri=meciRepository.findAll();
        List<Meci> meciuri= new ArrayList<>();

        for(Meci meci: iterableMeciuri){
            ClubSportiv clubSportiv=clubSportivService.getCluburiSportiveDupaNume(meci.getNumeAdversar());

            meciuri.add(Meci.builder()
                    .id(meci.getId())
                    .idEditie(meci.getIdEditie())
                    .numeEditie(meci.getNumeEditie())
                    .status(meci.getStatus())
                    .data(meci.getData())
                    .numeAdversar(meci.getNumeAdversar())
                    .logoAdversar(clubSportiv.getLogo().length()!=0?clubSportiv.getLogo():"")
                    .locatie(meci.getLocatie())
                    .scorCSM(meci.getScorCSM())
                    .scorAdversar(meci.getScorAdversar())
                    .teren(meci.getTeren())
                    .link(meci.getLink())
                    .build());
        }
        return meciuri;
    }

    public Meci getMeciDupaId(Long id){
        return meciRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

    }

    public List<Meci> getMeciuriFiltrate(EnumStatusMeci status, String idCampionat, String data){
        Iterable<Meci> iterableMeciuri=meciRepository.findAll();
        List<Meci> meciuri= new ArrayList<>();

        for(Meci meci: iterableMeciuri){
            ClubSportiv clubSportiv=clubSportivService.getCluburiSportiveDupaNume(meci.getNumeAdversar());

            meciuri.add(Meci.builder()
                    .id(meci.getId())
                    .idEditie(meci.getIdEditie())
                    .numeEditie(meci.getNumeEditie())
                    .status(meci.getStatus())
                    .data(meci.getData())
                    .numeAdversar(meci.getNumeAdversar())
                    .logoAdversar(clubSportiv.getLogo().length()!=0?clubSportiv.getLogo():"")
                    .locatie(meci.getLocatie())
                    .scorCSM(meci.getScorCSM())
                    .scorAdversar(meci.getScorAdversar())
                    .teren(meci.getTeren())
                    .link(meci.getLink())
                    .build());
        }

        return meciuri.stream().filter(meci->meci.getStatus().equals(status))
                .filter(meci->meci.getIdEditie().toString().equals(idCampionat))
                .filter(data.length()!=0?meci->meci.getData().split(" ")[0].equals(data):meci->true)
                .toList();
    }

    public Meci addMeci(MeciDto meciDto){
        ClubSportiv clubSportiv=clubSportivService.getCluburiSportiveDupaNume(meciDto.getNumeAdversar());
        Editie editie=editieRepository.findById(meciDto.getIdEditie()).orElseThrow(()->{
            throw new CrudOperationException("Editia nu exista");
        });

        Meci meci=Meci.builder()
                .idEditie(meciDto.getIdEditie())
                .numeEditie(editie.getNumeEditie())
                .status(meciDto.getScorCSM().length()!=0&&meciDto.getScorAdversar().length()!=0? EnumStatusMeci.REZULTAT:EnumStatusMeci.VIITOR)
                .data(meciDto.getData())
                .numeAdversar(meciDto.getNumeAdversar())
                .logoAdversar(clubSportiv.getLogo().length()!=0? clubSportiv.getLogo() : "")
                .locatie(meciDto.getLocatie())
                .scorCSM(meciDto.getScorCSM())
                .scorAdversar(meciDto.getScorAdversar())
                .teren(meciDto.getTeren())
                .link(meciDto.getLink())
                .build();

        meciRepository.save(meci);
        return meci;
    }

    public Meci updateMeci(Long idMeci, MeciDto meciDto){
        Meci meci = meciRepository.findById(idMeci).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        ClubSportiv clubSportiv=clubSportivService.getCluburiSportiveDupaNume(meciDto.getNumeAdversar());
        Editie editie=editieRepository.findById(meciDto.getIdEditie()).orElseThrow(()->{
            throw new CrudOperationException("Editia nu exista");
        });

        meci.setIdEditie(meciDto.getIdEditie());
        meci.setNumeEditie(editie.getNumeEditie());
        meci.setStatus(meciDto.getScorCSM().length()!=0&&meciDto.getScorAdversar().length()!=0? EnumStatusMeci.REZULTAT:EnumStatusMeci.VIITOR);
        meci.setData(meciDto.getData());
        meci.setNumeAdversar(meciDto.getNumeAdversar());
        meci.setLogoAdversar(clubSportiv.getLogo().length()!=0? clubSportiv.getLogo() : "");
        meci.setLocatie(meciDto.getLocatie());
        meci.setScorCSM(meciDto.getScorCSM());
        meci.setScorAdversar(meciDto.getScorAdversar());
        meci.setTeren(meciDto.getTeren());
        meci.setLink(meciDto.getLink());

        meciRepository.save(meci);
        return meci;
    }

    public void deleteMeci(Long id){
        Meci meci=meciRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        meciRepository.delete(meci);
    }
}
