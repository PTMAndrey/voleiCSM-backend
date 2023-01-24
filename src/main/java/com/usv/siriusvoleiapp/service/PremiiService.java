package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.PremiiDto;
import com.usv.siriusvoleiapp.entity.Divizie;
import com.usv.siriusvoleiapp.entity.Premii;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.DivizieRepository;
import com.usv.siriusvoleiapp.repository.PremiiRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PremiiService {
    public static final String MESAJ_DE_EROARE = "Premiul nu exista";

    private final PremiiRepository premiiRepository;
    private final DivizieRepository divizieRepository;

    public PremiiService(PremiiRepository premiiRepository, DivizieRepository divizieRepository) {
        this.premiiRepository = premiiRepository;
        this.divizieRepository = divizieRepository;
    }

    public List<Premii> getPremii(){
        Iterable<Premii> iterablePremii=premiiRepository.findAll();
        List<Premii> premii = new ArrayList<>();

        iterablePremii.forEach(pr->
                premii.add(Premii.builder()
                        .id(pr.getId())
                        .denumire(pr.getDenumire())
                        .locCampionat(pr.getLocCampionat())
                        .an(pr.getAn())
                        .idEditie(pr.getIdEditie())
                        .idDivizie(pr.getIdDivizie())
                        .numeDivizie(pr.getNumeDivizie())
                        .build()));
        return premii;
    }

    public Premii getPremiiDupaId(Long idPremiu){
        return premiiRepository.findById(idPremiu).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });
    }

    public List<Premii> getPremiiFiltrate(String divizie){
        Iterable<Premii> iterablePremii=premiiRepository.findAll();
        List<Premii> premii = new ArrayList<>();

        iterablePremii.forEach(pr->
                premii.add(Premii.builder()
                        .id(pr.getId())
                        .denumire(pr.getDenumire())
                        .locCampionat(pr.getLocCampionat())
                        .an(pr.getAn())
                        .idEditie(pr.getIdEditie())
                        .idDivizie(pr.getIdDivizie())
                        .numeDivizie(pr.getNumeDivizie())
                        .build()));
        return premii.stream().filter(pr->pr.getNumeDivizie().equals(divizie)).toList();
    }

    public Premii addPremii(PremiiDto premiiDto){
        Divizie divizie=divizieRepository.findById(premiiDto.getIdDivizie()).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        Premii premii=Premii.builder()
                .denumire(premiiDto.getDenumire())
                .locCampionat(premiiDto.getLocCampionat())
                .an(premiiDto.getAn())
                .idEditie(premiiDto.getIdEditie())
                .idDivizie(premiiDto.getIdDivizie())
                .numeDivizie(divizie.getDenumireDivizie().toString())
                .build();

        premiiRepository.save(premii);
        return premii;
    }

    public Premii updatePremii(Long idPremiu, PremiiDto premiiDto){
        Premii premii = premiiRepository.findById(idPremiu).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        premii.setDenumire(premiiDto.getDenumire());
        premii.setLocCampionat(premiiDto.getLocCampionat());
        premii.setAn(premiiDto.getAn());
        premii.setIdEditie(premiiDto.getIdEditie());
        premii.setIdDivizie(premiiDto.getIdDivizie());

        premiiRepository.save(premii);
        return premii;
    }

    public void deletePremii(Long id){
        Premii premii = premiiRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        premiiRepository.delete(premii);
    }

}
