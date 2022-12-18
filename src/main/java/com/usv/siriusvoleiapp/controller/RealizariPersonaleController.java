package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.entity.RealizariPersonale;
import com.usv.siriusvoleiapp.service.RealizariPersonaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/realizariPersonale")
public class RealizariPersonaleController {
    private final RealizariPersonaleService realizariPersonaleService;

    public RealizariPersonaleController(RealizariPersonaleService realizariPersonaleService) {
        this.realizariPersonaleService = realizariPersonaleService;
    }

    @GetMapping
    public ResponseEntity<List<RealizariPersonale>> getRealizariPersonale(){
        return ResponseEntity.ok(realizariPersonaleService.getRealizariPersonale());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<RealizariPersonale>> getRealizariPersonale(@PathVariable UUID id){
        return ResponseEntity.ok(realizariPersonaleService.getRealizariPersoana(id));
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Persoana> adaugaRealizariPersonale(@PathVariable UUID id, @RequestBody List<RealizariPersonale> realizariPersonale) {
        return ResponseEntity.ok(realizariPersonaleService.adaugaRealizariPersonale(id,realizariPersonale));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Persoana> updateRealizariPersonale(@PathVariable UUID id, @RequestBody List<RealizariPersonale> realizariPersonale) {
        return ResponseEntity.ok(realizariPersonaleService.updateRealizariPersonale(id,realizariPersonale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRealizarePersonala(@PathVariable long id){
        realizariPersonaleService.deleteRealizarePersonala(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
