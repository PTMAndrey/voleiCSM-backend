package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.dto.PersoanaDto;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.service.DivizieService;
import com.usv.siriusvoleiapp.service.PersoanaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/persoana")
@CrossOrigin(origins = "http://localhost:3000")
public class PerosanaController {
    private final PersoanaService persoanaService;

    public PerosanaController(PersoanaService persoanaService) {
        this.persoanaService = persoanaService;
    }

    @GetMapping
    public ResponseEntity<List<PersoanaDto>> getPersoane(){
        return ResponseEntity.ok(persoanaService.getPersoane());
    }

    @PostMapping
    public ResponseEntity<Persoana> addClubSportiv(@RequestParam("file") MultipartFile file, @ModelAttribute PersoanaDto persoanaDto) throws IOException {
        return ResponseEntity.ok(persoanaService.addPersoana(file,persoanaDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persoana> updatePersoana(@PathVariable Long id, @ModelAttribute PersoanaDto persoanaDto, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(persoanaService.updatePersoana(id,persoanaDto,file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersoana(@PathVariable Long id){
        persoanaService.deletePersoana(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
