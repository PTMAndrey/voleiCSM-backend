package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusMeci;
import com.usv.siriusvoleiapp.dto.MeciDto;
import com.usv.siriusvoleiapp.entity.Meci;
import com.usv.siriusvoleiapp.service.MeciService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meci")
public class MeciController {
    private final MeciService meciService;

    public MeciController(MeciService meciService) {
        this.meciService = meciService;
    }

    @GetMapping
    public ResponseEntity<List<Meci>> getEditii(){
        return ResponseEntity.ok(meciService.getMeciuri());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meci> getMeciDupaId(@PathVariable Long id){
        return ResponseEntity.ok(meciService.getMeciDupaId(id));
    }

    @GetMapping("/filtru")
    public ResponseEntity<List<Meci>> getMeciuriFiltrate(@RequestParam("status") EnumStatusMeci status, @RequestParam("campionat") String idCampionat, @RequestParam("dataSpecifica") Optional<String> dataSpecifica){
        return dataSpecifica.map(s -> ResponseEntity.ok(meciService.getMeciuriFiltrate(status, idCampionat, s))).orElseGet(() -> ResponseEntity.ok(meciService.getMeciuriFiltrate(status, idCampionat, "")));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Meci>> getMeciuriDupaStatus(@RequestParam("status") EnumStatusMeci status){
        return ResponseEntity.ok(meciService.getMeciuriDupaStatus(status));
    }

    @PostMapping
    public ResponseEntity<Meci> addEditie(@RequestBody MeciDto meciDto) {
        return ResponseEntity.ok(meciService.addMeci(meciDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meci> updateMeci(@PathVariable Long id, @RequestBody MeciDto meciDto ){
        return ResponseEntity.ok(meciService.updateMeci(id,meciDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeci(@PathVariable Long id){
        meciService.deleteMeci(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
