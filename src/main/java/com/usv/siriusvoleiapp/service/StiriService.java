package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.dto.StiriDto;
import com.usv.siriusvoleiapp.entity.Stiri;
import com.usv.siriusvoleiapp.repository.StiriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        String numeImaginiStiri=azureBlobAdapter.uploadMultipleFile(multipartFiles);
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

}
