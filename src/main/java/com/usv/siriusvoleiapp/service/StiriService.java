package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusStire;
import com.usv.siriusvoleiapp.dto.StiriDto;
import com.usv.siriusvoleiapp.entity.Stiri;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.StiriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StiriService {
    @Autowired
    private AzureBlobService azureBlobAdapter;

    private final StiriRepository stiriRepository;

    public StiriService(StiriRepository stiriRepository) {
        this.stiriRepository = stiriRepository;
    }


    public List<Stiri> getStiri(String statusCerut){
        Iterable<Stiri> iterableStiri=stiriRepository.findAll();
        List<Stiri> stiri= new ArrayList<>();

        iterableStiri.forEach(stire->
                stiri.add(Stiri.builder()
                                .idStiri(stire.getIdStiri())
                                .titlu(stire.getTitlu())
                                .descriere(stire.getDescriere())
                                .status(stire.getStatus())
                                .dataPublicarii(stire.getDataPublicarii())
                                .imagini(stire.getImagini())
                                .imaginiURL(
                                        Arrays.stream(stire.getImagini().split(", ")).map(img->
                                                        azureBlobAdapter.getFileURL(img)+" "
                                                ).collect(Collectors.toList())
                                )
                        .build()));

        if(statusCerut.equals("TOATE"))
            return stiri;
        else
            return stiri.stream().filter(stire->stire.getStatus().toString().equals(statusCerut)).collect(Collectors.toList());
    }

    public Stiri addStire (List<MultipartFile> multipartFiles, StiriDto stiriDto) throws IOException {
        String numeImaginiStiri = null;

        if(multipartFiles.get(0).getSize()!=0)
            numeImaginiStiri=azureBlobAdapter.uploadMultipleFile(multipartFiles);
        Stiri stiri= Stiri.builder()
                .titlu(stiriDto.getTitlu())
                .descriere(stiriDto.getDescriere())
                .status(stiriDto.getStatus())
                .dataPublicarii(stiriDto.getDataPublicarii())
                .imagini(numeImaginiStiri)
                .build();
        stiriRepository.save(stiri);
        return  stiri;
    }

    public Stiri updateStire(Long id, StiriDto stiriDto, List<MultipartFile> multipartFiles) throws IOException {
        Stiri stire=stiriRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Stirea nu exista");
        });
        String numeImaginiStiri=stire.getImagini();

        if(!multipartFiles.containsAll(Arrays.stream(stire.getImagini().split(", ")).toList()))
        {
            List<String> imagini= Arrays.stream(stire.getImagini().split(", ")).collect(Collectors.toList());
            for(int i=0; i<imagini.size(); i++)
                azureBlobAdapter.deleteBlob(imagini.get(i));

            numeImaginiStiri=azureBlobAdapter.uploadMultipleFile(multipartFiles);
        }

        stire.setTitlu(stiriDto.getTitlu());
        stire.setDescriere(stiriDto.getDescriere());
        stire.setStatus(stiriDto.getStatus());
        stire.setDataPublicarii(stiriDto.getDataPublicarii());
        stire.setImagini(numeImaginiStiri);

        stiriRepository.save(stire);
        return stire;
    }

    public Stiri updateStatusStire(Long id, EnumStatusStire status){
        Stiri stire=stiriRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Stirea nu exista");
        });

        stire.setStatus(status);

        stiriRepository.save(stire);

        return stire;
    }

    public void deleteStire(Long id){
        Stiri stire=stiriRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Stirea nu exista");
        });

        List<String> imagini= Arrays.stream(stire.getImagini().split(", ")).collect(Collectors.toList());
        for(int i=0; i<imagini.size(); i++)
            azureBlobAdapter.deleteBlob(imagini.get(i));

        stiriRepository.delete(stire);
    }

}
