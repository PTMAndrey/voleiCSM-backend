package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.PremiiDto;
import com.usv.siriusvoleiapp.entity.Divizie;
import com.usv.siriusvoleiapp.entity.Editie;
import com.usv.siriusvoleiapp.entity.Premii;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.DivizieRepository;
import com.usv.siriusvoleiapp.repository.EditieRepository;
import com.usv.siriusvoleiapp.repository.PremiiRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PremiiService {
    public static final String MESAJ_DE_EROARE = "Premiul nu exista";

    private final PremiiRepository premiiRepository;
    private final DivizieRepository divizieRepository;

    private final EditieRepository editieRepository;

    public PremiiService(PremiiRepository premiiRepository, DivizieRepository divizieRepository, EditieRepository editieRepository) {
        this.premiiRepository = premiiRepository;
        this.divizieRepository = divizieRepository;
        this.editieRepository = editieRepository;
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
                        .numeEditie(pr.getNumeEditie())
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
                        .numeEditie(pr.getNumeEditie())
                        .build()));
        return premii.stream().filter(pr->pr.getNumeDivizie().equals(divizie)).toList();
    }

    public Premii addPremii(PremiiDto premiiDto){
        Divizie divizie=divizieRepository.findById(premiiDto.getIdDivizie()).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        Editie editie=editieRepository.findById(premiiDto.getIdEditie()).orElseThrow(()-> {
                    throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        Premii premii=Premii.builder()
                .denumire(premiiDto.getDenumire())
                .locCampionat(premiiDto.getLocCampionat())
                .an(premiiDto.getAn())
                .idEditie(premiiDto.getIdEditie())
                .idDivizie(premiiDto.getIdDivizie())
                .numeDivizie(divizie.getDenumireDivizie().toString())
                .numeEditie(editie.getNumeEditie())
                .build();

        premiiRepository.save(premii);
        return premii;
    }

    public Premii updatePremii(Long idPremiu, PremiiDto premiiDto){
        Premii premii = premiiRepository.findById(idPremiu).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        Divizie divizie=divizieRepository.findById(premiiDto.getIdDivizie()).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        Editie editie=editieRepository.findById(premiiDto.getIdEditie()).orElseThrow(()-> {
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        premii.setDenumire(premiiDto.getDenumire());
        premii.setLocCampionat(premiiDto.getLocCampionat());
        premii.setAn(premiiDto.getAn());
        premii.setIdEditie(premiiDto.getIdEditie());
        premii.setIdDivizie(premiiDto.getIdDivizie());

        premii.setNumeDivizie(divizie.getDenumireDivizie().toString());
        premii.setNumeEditie(editie.getNumeEditie());

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
