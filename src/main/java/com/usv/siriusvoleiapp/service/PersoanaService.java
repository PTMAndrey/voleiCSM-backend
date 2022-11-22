package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.PersoanaDto;
import com.usv.siriusvoleiapp.entity.IstoricPersoana;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.entity.RealizariPersonale;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.DivizieRepository;
import com.usv.siriusvoleiapp.repository.IstoricPersoanaRepository;
import com.usv.siriusvoleiapp.repository.PersoanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersoanaService {
    @Autowired
    private final AzureBlobService azureBlobAdapter;

    private final PersoanaRepository persoanaRepository;
    public final DivizieRepository divizieRepository;
    public final IstoricPersoanaRepository istoricPersoanaRepository;

    public PersoanaService(AzureBlobService azureBlobAdapter, PersoanaRepository persoanaRepository, DivizieRepository divizieRepository, IstoricPersoanaRepository istoricPersoanaRepository) {
        this.azureBlobAdapter = azureBlobAdapter;
        this.persoanaRepository = persoanaRepository;
        this.divizieRepository = divizieRepository;
        this.istoricPersoanaRepository = istoricPersoanaRepository;
    }

    @EntityGraph(value = "topic.all")
    public List<Persoana> getPersoane(){
        Iterable<Persoana> iterblePersoana=persoanaRepository.findAll();
        List<Persoana> persoane=new ArrayList<>();

        iterblePersoana.forEach(pers->
                persoane.add(Persoana.builder()
                                .id(pers.getId())
                                .imagine(azureBlobAdapter.getFileURL(pers.getImagine()))
                                .nume(pers.getNume())
                                .prenume(pers.getPrenume())
                                .dataNasterii(pers.getDataNasterii())
                                .inaltime(pers.getInaltime())
                                .nationalitate(pers.getNationalitate())
                                .personal(pers.getPersonal())
                                .post(pers.getPost())
                                .descriere(pers.getDescriere())
                                .numeDivizie(pers.getNumeDivizie())
                                .istoricPosturi(pers.getIstoricPosturi())
                                .build()));
        return persoane;
    }

    public Persoana addPersoana (MultipartFile file, PersoanaDto persoanaDto) throws IOException {
        String fileName = azureBlobAdapter.upload(file);
        Persoana persoana=Persoana.builder()
                .imagine(fileName)
                .nume(persoanaDto.getNume())
                .prenume(persoanaDto.getPrenume())
                .dataNasterii(persoanaDto.getDataNasterii())
                .inaltime(persoanaDto.getInaltime())
                .nationalitate(persoanaDto.getNationalitate())
                .personal(persoanaDto.getPersonal())
                .post(persoanaDto.getPost())
                .descriere(persoanaDto.getDescriere())
                .numeDivizie(persoanaDto.getNumeDivizie())
                .build();
        persoanaRepository.save(persoana);
        return persoana;
    }

    public Persoana updatePersoana(UUID id, PersoanaDto persoanaDto, MultipartFile file) throws IOException {
        Persoana persoana=persoanaRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });
        String fileName;
        if(!file.isEmpty()){
            azureBlobAdapter.deleteBlob(persoana.getImagine());
            fileName = azureBlobAdapter.upload(file);
        }
        else
            fileName=persoana.getImagine();
        persoana.setImagine(fileName);
        persoana.setNume(persoanaDto.getNume());
        persoana.setPrenume(persoanaDto.getPrenume());
        persoana.setDataNasterii(persoanaDto.getDataNasterii());
        persoana.setInaltime(persoanaDto.getInaltime());
        persoana.setNationalitate(persoanaDto.getNationalitate());
        persoana.setPersonal(persoanaDto.getPersonal());
        persoana.setPost(persoanaDto.getPost());
        persoana.setDescriere(persoanaDto.getDescriere());

        persoanaRepository.save(persoana);
        return persoana;
    }

    public void deletePersoana(UUID id){
        Persoana persoana=persoanaRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });

        azureBlobAdapter.deleteBlob(persoana.getImagine());
        persoanaRepository.delete(persoana);
    }

    public Persoana adaugaIstoricPersoana(UUID idPersoana, List<IstoricPersoana> istoricPersoana){
        Persoana persoana=persoanaRepository.findById(idPersoana).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });

        if(persoana.getIstoricPosturi()==null)
            persoana.setIstoricPosturi(new ArrayList<>());

        for (IstoricPersoana ist:istoricPersoana) {
            ist.setId(idPersoana);
        }
//        persoana.setIstoricPosturi(new ArrayList<>(istoricPersoana));

        istoricPersoana.stream().map(pers->
                        persoana.getIstoricPosturi().add(pers))
                .collect(Collectors.toSet());
        persoanaRepository.save(persoana);
        return persoana;
    }

    public Persoana adaugaRealizariPersonale(UUID idPersoana, List<RealizariPersonale> realizariPersonale){
        Persoana persoana=persoanaRepository.findById(idPersoana).orElseThrow(()->{
            throw new CrudOperationException("Persoana nu exista");
        });

        if(persoana.getRealizariPersonale()==null)
            persoana.setRealizariPersonale(new ArrayList<>());

        for (RealizariPersonale realizari:realizariPersonale) {
            realizari.setId(idPersoana);
        }

        realizariPersonale.stream().map(pers->
                        persoana.getRealizariPersonale().add(pers))
                .collect(Collectors.toSet());

        persoanaRepository.save(persoana);
        return persoana;
    }
}
