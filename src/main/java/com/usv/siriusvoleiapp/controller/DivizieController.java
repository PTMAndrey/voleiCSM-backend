package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.dto.ClubSportivDto;
import com.usv.siriusvoleiapp.dto.DivizieDto;
import com.usv.siriusvoleiapp.service.DivizieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DivizieController {
    private final DivizieService divizieService;

    public DivizieController(DivizieService divizieService) {
        this.divizieService = divizieService;
    }

    @GetMapping
    public ResponseEntity<List<DivizieDto>> getDivizii(){
        return ResponseEntity.ok(divizieService.getDivizii());
    }

    @PostMapping
    public ResponseEntity<DivizieDto> addDivizie(@RequestBody DivizieDto divizieDto){
        return ResponseEntity.ok(divizieService.addDivizie(divizieDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DivizieDto> updateDivizie(@PathVariable Long id, @RequestBody DivizieDto divizieDto){
        return ResponseEntity.ok(divizieService.updateDivizie(id,divizieDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDivizie(@PathVariable Long id){
        divizieService.deleteDivizie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
