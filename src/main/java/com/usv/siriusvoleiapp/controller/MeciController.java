package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusMeci;
import com.usv.siriusvoleiapp.dto.MeciDto;
import com.usv.siriusvoleiapp.entity.Meci;
import com.usv.siriusvoleiapp.service.MeciService;
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

    @GetMapping("/filtru")
    public ResponseEntity<List<Meci>> getMeciuriFiltrate(@RequestParam("status") EnumStatusMeci status, @RequestParam("campionat") String idCampionat, @RequestParam("dataSpecifica") Optional<String> dataSpecifica){
        if(dataSpecifica.isPresent())
            return ResponseEntity.ok(meciService.getMeciuriFiltrate(status, idCampionat, dataSpecifica.get()));
        else
            return ResponseEntity.ok(meciService.getMeciuriFiltrate(status, idCampionat, ""));
    }

    @PostMapping
    public ResponseEntity<Meci> addEditie(@ModelAttribute MeciDto meciDto) {
        return ResponseEntity.ok(meciService.addMeci(meciDto));
    }

}
