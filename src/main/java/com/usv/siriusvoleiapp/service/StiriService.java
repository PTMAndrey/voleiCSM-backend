package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusStire;
import com.usv.siriusvoleiapp.declaratieEnum.EnumTipStire;
import com.usv.siriusvoleiapp.dto.StiriDto;
import com.usv.siriusvoleiapp.entity.Stiri;
import com.usv.siriusvoleiapp.exceptions.CrudOperationException;
import com.usv.siriusvoleiapp.repository.StiriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class StiriService {
    @Autowired
    private AzureBlobService azureBlobAdapter;

    private final StiriRepository stiriRepository;

    public StiriService(StiriRepository stiriRepository) {
        this.stiriRepository = stiriRepository;
    }


    public List<Stiri> getStiri(EnumStatusStire statusCerut){
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
                                .videoclipuri(null)
                        .build()));

        if(statusCerut.toString().equals("TOATE"))
            return stiri;
        else
            return stiri.stream().filter(stire->stire.getStatus().equals(statusCerut)).collect(Collectors.toList());
    }

    public Stiri getStireDupaId(UUID idStire){

        Stiri stire=stiriRepository.findById(idStire).orElseThrow(()->{
            throw new CrudOperationException("Stirea nu exista");
        });

        stire.setImaginiURL(stire.getImagini()!=null?
                Arrays.stream(stire.getImagini().split(", ")).map(img->
                        azureBlobAdapter.getFileURL(img)+" "
                ).collect(Collectors.toList()):null);

        return stire;
    }

    public List<Stiri> getStiriFiltrate(EnumStatusStire statusCerut, EnumTipStire tipStire, String numarZile, String perioadaSpecifica, String dataSpecifica) throws ParseException {

        Iterable<Stiri> iterableStiri=stiriRepository.findAll();
        List<Stiri> stiri= new ArrayList<>();

        String[] perioada= null;
        long interval = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate=sdf.parse(formatter.format(new Date()));

        if(numarZile.length()!=0 && dataSpecifica.length()!=0 || numarZile.length()!=0 && perioadaSpecifica.length()!=0 || perioadaSpecifica.length()!=0 && dataSpecifica.length()!=0)
            throw new CrudOperationException("Este posibil un singur filtru");

        if(perioadaSpecifica.length()!=0){
            perioada=perioadaSpecifica.split(" ");
            interval=TimeUnit.DAYS.convert(Math.abs(sdf.parse(perioada[0]).getTime() - sdf.parse(perioada[1]).getTime()), TimeUnit.MILLISECONDS);
        }

        for(Stiri stire: iterableStiri){
            if(numarZile.length()!=0 && TimeUnit.DAYS.convert(Math.abs(currentDate.getTime() - sdf.parse(stire.getDataPublicarii()).getTime()), TimeUnit.MILLISECONDS) <= Integer.parseInt(numarZile))
            {
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
                        .build());
            }
            if(perioadaSpecifica.length()!=0 && TimeUnit.DAYS.convert(Math.abs(sdf.parse(stire.getDataPublicarii()).getTime()- sdf.parse(perioada[0]).getTime()), TimeUnit.MILLISECONDS) <= interval)
            {
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
                        .build());
            }
            if(dataSpecifica.length()!=0 && stire.getDataPublicarii().equals(dataSpecifica)){
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
                        .build());
            }

            if(numarZile.length()==0 && perioadaSpecifica.length()==0 && dataSpecifica.length()==0)
            {
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
                        .build());
            }
        }


        if(tipStire.toString().equals("IMAGINE"))
            stiri=stiri.stream().filter(stire->stire.getVideoclipuri()==null).collect(Collectors.toList());
        else if(tipStire.toString().equals("VIDEO"))
            stiri=stiri.stream().filter(stire->stire.getImagini()==null).collect(Collectors.toList());
        else if(tipStire.toString().equals("TEXT"))
            stiri=stiri.stream().filter(stire->stire.getImagini()==null && stire.getImagini()==null).collect(Collectors.toList());


        if(statusCerut.toString().equals("TOATE"))
            return stiri;
        else
            return stiri.stream().filter(stire->stire.getStatus().equals(statusCerut)).collect(Collectors.toList());
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
                .videoclipuri(null)
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
        stire.setVideoclipuri(null);

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
