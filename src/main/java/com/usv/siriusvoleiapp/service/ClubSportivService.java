package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.ClubSportivDto;
import com.usv.siriusvoleiapp.dto.DivizieDto;
import com.usv.siriusvoleiapp.dto.PersoanaDto;
import com.usv.siriusvoleiapp.entity.ClubSportiv;
import com.usv.siriusvoleiapp.entity.Divizie;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.ClubSportivRepository;
import com.usv.siriusvoleiapp.repository.DivizieRepository;
import com.usv.siriusvoleiapp.repository.PersoanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClubSportivService {
    @Autowired
    private AzureBlobService azureBlobAdapter;

    public final ClubSportivRepository clubSportivRepository;
    public final DivizieRepository divizieRepository;
    private final PersoanaRepository persoanaRepository;

    public ClubSportivService(ClubSportivRepository clubSportivRepository, DivizieRepository divizieRepository, PersoanaRepository persoanaRepository) {
        this.clubSportivRepository = clubSportivRepository;
        this.divizieRepository = divizieRepository;
        this.persoanaRepository = persoanaRepository;
    }

    public List<ClubSportiv> getCluburiSportive(){
        Iterable<ClubSportiv> iterableCluburiSportive = clubSportivRepository.findAll();
        List<ClubSportiv> cluburiSportive= new ArrayList<>();

        iterableCluburiSportive.forEach(clubSportiv ->
                cluburiSportive.add(ClubSportiv.builder()
                                .idClubSportiv(clubSportiv.getIdClubSportiv())
                                .numeClubSportiv(clubSportiv.getNumeClubSportiv())
                                .logo(azureBlobAdapter.getFileURL(clubSportiv.getLogo()))
                                .viziuneClubSportiv(clubSportiv.getViziuneClubSportiv())
                                .istorieClubSportiv(clubSportiv.getIstorieClubSportiv())
                                .build()));
        return cluburiSportive;
    }

    public ClubSportivDto addClubSportiv(MultipartFile file, ClubSportivDto clubSportivDto) throws IOException {
        String fileName = azureBlobAdapter.upload(file);
        ClubSportiv clubSportiv=ClubSportiv.builder()
                .numeClubSportiv(clubSportivDto.getNumeClubSportiv())
                .logo(fileName)
                .viziuneClubSportiv(clubSportivDto.getViziuneClubSportiv())
                .istorieClubSportiv(clubSportivDto.getIstorieClubSportiv())
                .build();
        clubSportivRepository.save(clubSportiv);
        return clubSportivDto;
    }

    public ClubSportivDto updateClubSportiv(Long id, ClubSportivDto clubSportivDto, MultipartFile file) throws IOException {
//        WORNING
        ClubSportiv clubSportiv=clubSportivRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Clubul sportiv nu exista");
        });
        String fileName;
        if(!file.isEmpty()){
            azureBlobAdapter.deleteBlob(clubSportivDto.getLogo());
            fileName = azureBlobAdapter.upload(file);
        }
        else
            fileName=clubSportivDto.getLogo();

        clubSportiv.setNumeClubSportiv(clubSportivDto.getNumeClubSportiv());
        clubSportiv.setLogo(fileName);
        clubSportiv.setIstorieClubSportiv(clubSportivDto.getIstorieClubSportiv());
        clubSportiv.setViziuneClubSportiv(clubSportivDto.getViziuneClubSportiv());

        clubSportivRepository.save(clubSportiv);
        return clubSportivDto;
    }

    public void deleteClubSportiv(Long id){
        ClubSportiv clubSportiv=clubSportivRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Clubul sportiv nu exista");
        });

        azureBlobAdapter.deleteBlob(clubSportiv.getLogo());
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

    public List<DivizieDto> getDiviziiClubSportiv(Long idClub){
        ClubSportiv clubSportiv=clubSportivRepository.findById(idClub).orElseThrow(()->{
            throw new CrudOperationException("Clubul sportiv nu exista");
        });

        List<Divizie> divizii = clubSportiv.getDivizii();

        return divizii.stream()
                .map(divizie-> DivizieDto.builder()
                        .idDivizie(divizie.getIdDivizie())
                        .denumireDivizie(divizie.getDenumireDivizie())
                        .build())
                .collect(Collectors.toList());
    }

    public ClubSportiv adaugarePersoanaLaClubSportiv(Long idClubSportiv, UUID idPersoana){
        ClubSportiv clubSportiv=clubSportivRepository.findById(idClubSportiv).orElseThrow(()->{
            throw new CrudOperationException("Clubul sportiv nu exista");
        });

        Persoana persoana=persoanaRepository.findById(idPersoana).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });

        if(clubSportiv.getPersoane()==null)
            clubSportiv.setPersoane(new ArrayList<>());

        clubSportiv.getPersoane().add(persoana);
        clubSportivRepository.save(clubSportiv);

        return clubSportiv;
    }

    public List<PersoanaDto> getPersoaneClubSportiv(Long idClub){
        ClubSportiv clubSportiv=clubSportivRepository.findById(idClub).orElseThrow(()->{
            throw new CrudOperationException("Clubul sportiv nu exista");
        });

        List<Persoana> persoane = clubSportiv.getPersoane();

        return persoane.stream()
                .map(pers-> PersoanaDto.builder()
                        .imagine(azureBlobAdapter.getFileURL(pers.getImagine()))
                        .nume(pers.getNume())
                        .prenume(pers.getPrenume())
                        .dataNasterii(pers.getDataNasterii())
                        .inaltime(pers.getInaltime())
                        .nationalitate(pers.getNationalitate())
                        .post(pers.getPost())
                        .descriere(pers.getDescriere())
                        .numeDivizie(pers.getNumeDivizie())
                        .build())
                .collect(Collectors.toList());
    }
}
