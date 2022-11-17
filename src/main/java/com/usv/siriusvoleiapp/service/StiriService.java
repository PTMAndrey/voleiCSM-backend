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
                                .id(stire.getId())
                                .titlu(stire.getTitlu())
                                .descriere(stire.getDescriere())
                                .status(stire.getStatus())
                                .dataPublicarii(stire.getDataPublicarii())
                                .imagini(stire.getImagini())
                                .imaginiURL(stire.getImagini()!=null?
                                        Arrays.stream(stire.getImagini().split(", ")).map(img->
                                                        azureBlobAdapter.getFileURL(img)+" "
                                                ).collect(Collectors.toList()):null
                                )
                        .build()));

        if(statusCerut.equals("TOATE"))
            return stiri;
        else
            return stiri.stream().filter(stire->stire.getStatus().toString().equals(statusCerut)).collect(Collectors.toList());
    }

    public Stiri getStireDupaId(UUID idStire){

        return stiriRepository.findById(idStire).orElseThrow(()->{
            throw new CrudOperationException("Stirea nu exista");
        });
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

    public Stiri updateStire(UUID id, StiriDto stiriDto, List<MultipartFile> multipartFiles) throws IOException {
        Stiri stire=stiriRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Stirea nu exista");
        });
        String numeImaginiStiri=stire.getImagini();

        if(numeImaginiStiri.length()!=0)
        {
            List<String> imagini= Arrays.stream(stire.getImagini().split(", ")).collect(Collectors.toList());
            for (String s : imagini) azureBlobAdapter.deleteBlob(s);

            numeImaginiStiri=azureBlobAdapter.uploadMultipleFile(multipartFiles);
        }
        else
            numeImaginiStiri=azureBlobAdapter.uploadMultipleFile(multipartFiles);

        stire.setTitlu(stiriDto.getTitlu());
        stire.setDescriere(stiriDto.getDescriere());
        stire.setStatus(stiriDto.getStatus());
        stire.setDataPublicarii(stiriDto.getDataPublicarii());
        stire.setImagini(numeImaginiStiri);

        stiriRepository.save(stire);
        return stire;
    }

    public Stiri updateStatusStire(UUID id, EnumStatusStire status){
        Stiri stire=stiriRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Stirea nu exista");
        });

        stire.setStatus(status);

        stiriRepository.save(stire);

        return stire;
    }

    public void deleteStire(UUID id){
        Stiri stire=stiriRepository.findById(id).orElseThrow(()->{
            throw new CrudOperationException("Stirea nu exista");
        });

        List<String> imagini=new ArrayList<>();
        if(stire.getImagini()!=null)
        {
            imagini= Arrays.stream(stire.getImagini().split(", ")).collect(Collectors.toList());
            for (String s : imagini) azureBlobAdapter.deleteBlob(s);
        }

        stiriRepository.delete(stire);
    }

}
