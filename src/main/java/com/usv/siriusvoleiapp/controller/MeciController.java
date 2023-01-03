package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.dto.MeciDto;
import com.usv.siriusvoleiapp.entity.Meci;
import com.usv.siriusvoleiapp.service.MeciService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<Meci> addEditie(@RequestBody MeciDto meciDto) {
        return ResponseEntity.ok(meciService.addMeci(meciDto));
    }

}
