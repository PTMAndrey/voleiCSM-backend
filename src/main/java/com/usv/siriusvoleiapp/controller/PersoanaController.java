package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.declaratieEnum.EnumNumeDivizie;
import com.usv.siriusvoleiapp.declaratieEnum.EnumPersonal;
import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusStire;
import com.usv.siriusvoleiapp.declaratieEnum.EnumTipStire;
import com.usv.siriusvoleiapp.dto.PersoanaDto;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.entity.RealizariPersonale;
import com.usv.siriusvoleiapp.entity.Stiri;
import com.usv.siriusvoleiapp.service.PersoanaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/persoana")
public class PersoanaController {
    private final PersoanaService persoanaService;

    public PersoanaController(PersoanaService persoanaService) {
        this.persoanaService = persoanaService;
    }

    @GetMapping
    public ResponseEntity<List<Persoana>> getPersoane(){
        return ResponseEntity.ok(persoanaService.getPersoane());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persoana> getPersoanaDupaId(@PathVariable UUID id){
        return ResponseEntity.ok(persoanaService.getPersoanaDupaId(id));
    }

    @GetMapping("/filtru")
    public ResponseEntity<List<Persoana>> getPersonalFiltrat(@RequestParam("tipPersonal") EnumPersonal tipPersonal, @RequestParam("divizie") EnumNumeDivizie divizie, @RequestParam("nume") Optional<String> nume, @RequestParam("prenume") Optional<String> prenume) throws ParseException {
        if(nume.isPresent())
            return ResponseEntity.ok(persoanaService.getPersonalFiltrat(tipPersonal, divizie, nume.get(), ""));
        else
        if(prenume.isPresent())
            return ResponseEntity.ok(persoanaService.getPersonalFiltrat(tipPersonal, divizie, "", prenume.get()));
        else
            return ResponseEntity.ok(persoanaService.getPersonalFiltrat(tipPersonal, divizie, "", ""));
    }

    @PostMapping
    public ResponseEntity<Persoana> addClubSportiv(@RequestParam("file") MultipartFile file, @ModelAttribute PersoanaDto persoanaDto) throws IOException {
        return ResponseEntity.ok(persoanaService.addPersoana(file,persoanaDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persoana> updatePersoana(@PathVariable UUID id, @ModelAttribute PersoanaDto persoanaDto, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(persoanaService.updatePersoana(id,persoanaDto,file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersoana(@PathVariable UUID id){
        persoanaService.deletePersoana(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
