package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.dto.PremiiDto;
import com.usv.siriusvoleiapp.entity.Premii;
import com.usv.siriusvoleiapp.service.PremiiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/premii")
public class PremiiController {
    private final PremiiService premiiService;

    public PremiiController(PremiiService premiiService) {
        this.premiiService = premiiService;
    }

    @GetMapping
    public ResponseEntity<List<Premii>> getPremii(){
        return ResponseEntity.ok(premiiService.getPremii());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Premii> getPremiiDupaId(@PathVariable Long id){
        return ResponseEntity.ok(premiiService.getPremiiDupaId(id));
    }

    @GetMapping("/filtru")
    public ResponseEntity<List<Premii>> getPersonalFiltrat(@RequestParam("divizie") String divizie) {
        return ResponseEntity.ok(premiiService.getPremiiFiltrate(divizie));
    }

    @PostMapping
    public ResponseEntity<Premii> addPremii(@RequestBody PremiiDto premiiDto) {
        return ResponseEntity.ok(premiiService.addPremii(premiiDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Premii> updatePremii(@PathVariable Long id, @RequestBody PremiiDto premiiDto ){
        return ResponseEntity.ok(premiiService.updatePremii(id,premiiDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePremii(@PathVariable Long id){
        premiiService.deletePremii(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
