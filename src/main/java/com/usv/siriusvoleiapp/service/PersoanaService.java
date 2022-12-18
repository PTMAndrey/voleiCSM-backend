package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.PersoanaDto;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.DivizieRepository;
import com.usv.siriusvoleiapp.repository.IstoricPosturiRepository;
import com.usv.siriusvoleiapp.repository.PersoanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PersoanaService {
    public static final String MESAJ_DE_EROARE = "Hello, welcome to the server";

    @Autowired
    private final AzureBlobService azureBlobAdapter;

    private final PersoanaRepository persoanaRepository;
    public final DivizieRepository divizieRepository;
    public final IstoricPosturiRepository istoricPersoanaRepository;

    public PersoanaService(AzureBlobService azureBlobAdapter, PersoanaRepository persoanaRepository, DivizieRepository divizieRepository, IstoricPosturiRepository istoricPersoanaRepository) {
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
                                .imagine(pers.getImagine().length()!=0?azureBlobAdapter.getFileURL(pers.getImagine()):"")
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
                                .realizariPersonale(pers.getRealizariPersonale())
                                .build()));
        return persoane;
    }

    public Persoana getPersoanaDupaId(UUID id){
        Persoana persoana=persoanaRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        persoana.setImagine(persoana.getImagine().length()!=0?azureBlobAdapter.getFileURL(persoana.getImagine()):"");

        return persoana;
    }

    public Persoana addPersoana (MultipartFile file, PersoanaDto persoanaDto) throws IOException {
        String fileName="";
        if(!file.isEmpty())
            fileName = azureBlobAdapter.upload(file);
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

        if(persoana.getImagine().length()!=0)
            azureBlobAdapter.deleteBlob(persoana.getImagine());
        persoanaRepository.delete(persoana);
    }
}
