package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.PersoanaDto;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.DivizieRepository;
import com.usv.siriusvoleiapp.repository.PersoanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersoanaService {
    @Autowired
    private AzureBlobService azureBlobAdapter;

    private final PersoanaRepository persoanaRepository;
    public final DivizieRepository divizieRepository;

    private final DivizieService divizieService;

    public PersoanaService(PersoanaRepository persoanaRepository, DivizieRepository divizieRepository, DivizieService divizieService) {
        this.persoanaRepository = persoanaRepository;
        this.divizieRepository = divizieRepository;
        this.divizieService = divizieService;
    }

    @EntityGraph(value = "topic.all")
    public List<PersoanaDto> getPersoane(){
        Iterable<Persoana> iterblePersoana=persoanaRepository.findAll();
        List<PersoanaDto> persoane=new ArrayList<>();

        iterblePersoana.forEach(pers->
                persoane.add(PersoanaDto.builder()
                                .imagine(azureBlobAdapter.getFileURL(pers.getImagine()))
                                .nume(pers.getNume())
                                .prenume(pers.getPrenume())
                                .dataNasterii(pers.getDataNasterii())
                                .inalitime(pers.getInalitime())
                                .nationalitate(pers.getNationalitate())
                                .post(pers.getPost())
                                .descriere(pers.getDescriere())
                                .numeDivizie(pers.getNumeDivizie())
                                .build()));
        return persoane;
    }

    public Persoana addPersoana (MultipartFile file, PersoanaDto persoanaDto) throws IOException {
//        Divizie divizie=divizieRepository.findById(persoanaDto.getIdDivizie().getIdDivizie()).orElseThrow(()->{
//            throw new CrudOperationException("Divizia nu exista");
//        });
        String fileName = azureBlobAdapter.upload(file);
        Persoana persoana=Persoana.builder()
                .imagine(fileName)
                .nume(persoanaDto.getNume())
                .prenume(persoanaDto.getPrenume())
                .dataNasterii(persoanaDto.getDataNasterii())
                .inalitime(persoanaDto.getInalitime())
                .nationalitate(persoanaDto.getNationalitate())
                .post(persoanaDto.getPost())
                .descriere(persoanaDto.getDescriere())
                .numeDivizie(persoanaDto.getNumeDivizie())
                .build();
//        divizieService.adaugarePersoanaLaDivizie(persoanaDto.getIdDivizie().getIdDivizie(),persoana.getIdPersoana());
        persoanaRepository.save(persoana);
        return persoana;
    }

    public Persoana updatePersoana(Long id, PersoanaDto persoanaDto, MultipartFile file) throws IOException {
        Persoana persoana=persoanaRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });
        String fileName;
        if(!file.isEmpty()){
            fileName = azureBlobAdapter.upload(file);
            azureBlobAdapter.deleteBlob(persoana.getImagine());
        }
        else
            fileName=persoana.getImagine();
        persoana.setImagine(fileName);
        persoana.setNume(persoanaDto.getNume());
        persoana.setPrenume(persoanaDto.getPrenume());
        persoana.setDataNasterii(persoanaDto.getDataNasterii());
        persoana.setInalitime(persoanaDto.getInalitime());
        persoana.setNationalitate(persoanaDto.getNationalitate());
        persoana.setPost(persoanaDto.getPost());
        persoana.setDescriere(persoanaDto.getDescriere());

        persoanaRepository.save(persoana);
        return persoana;
    }

    public void deletePersoana(Long id){
        Persoana persoana=persoanaRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });

        azureBlobAdapter.deleteBlob(persoana.getImagine());
        persoanaRepository.delete(persoana);
    }
}
